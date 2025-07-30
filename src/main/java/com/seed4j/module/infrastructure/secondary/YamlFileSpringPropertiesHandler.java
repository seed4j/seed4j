package com.seed4j.module.infrastructure.secondary;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.yaml.snakeyaml.comments.CommentType.BLOCK;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.javaproperties.Comment;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

class YamlFileSpringPropertiesHandler {

  private static final Map<Class<?>, Tag> TAG_BY_JAVA_TYPE = buildTagByJavaType();

  private final Path file;
  private final Indentation indentation;
  private final Yaml yaml;

  public YamlFileSpringPropertiesHandler(Path file, Indentation indentation) {
    this.indentation = indentation;
    Assert.notNull("file", file);

    this.file = file;
    this.yaml = createYaml();
  }

  private static Map<Class<?>, Tag> buildTagByJavaType() {
    return Map.of(Integer.class, Tag.INT, Long.class, Tag.INT, Double.class, Tag.FLOAT, Float.class, Tag.FLOAT, Boolean.class, Tag.BOOL);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  public void setValue(PropertyKey key, PropertyValue value) {
    Assert.notNull("key", key);
    Assert.notNull("value", value);

    try {
      MappingNode configuration = loadConfiguration();
      appendPropertyToConfiguration(key, value, configuration);
      saveConfiguration(configuration);
    } catch (IOException exception) {
      throw GeneratorException.technicalError("Error updating Yaml properties: " + exception.getMessage(), exception);
    }
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  public void setComment(PropertyKey key, Comment comment) {
    Assert.notNull("key", key);
    Assert.notNull("comment", comment);

    try {
      MappingNode configuration = loadConfiguration();
      addCommentToConfiguration(key, comment, configuration);
      saveConfiguration(configuration);
    } catch (IOException exception) {
      throw GeneratorException.technicalError("Error updating Yaml properties: " + exception.getMessage(), exception);
    }
  }

  private void appendPropertyToConfiguration(PropertyKey key, PropertyValue value, MappingNode configuration) {
    String localKey = extractKeysParts(key).getLast();

    List<NodeTuple> parentValue = parentPropertyNode(key, configuration).getValue();

    NodeTuple nodeProperty = new NodeTuple(buildScalarNode(localKey), buildValueNode(value));
    indexInCollection(parentValue, localKey).ifPresentOrElse(
      index -> parentValue.set(index, nodeProperty),
      () -> parentValue.add(nodeProperty)
    );
  }

  private Optional<Integer> indexInCollection(List<NodeTuple> nodesTuples, String key) {
    Predicate<NodeTuple> matchesKey = nodeTupleKeyEquals(key);
    for (int i = 0; i < nodesTuples.size(); i++) {
      if (matchesKey.test(nodesTuples.get(i))) {
        return Optional.of(i);
      }
    }
    return Optional.empty();
  }

  private Node buildValueNode(PropertyValue value) {
    if (value.values().size() > 1) {
      List<Node> nodes = value.values().stream().map(this::buildScalarNode).toList();

      return new SequenceNode(Tag.SEQ, nodes, FlowStyle.AUTO);
    }

    return buildScalarNode(value.values().iterator().next());
  }

  private void addCommentToConfiguration(PropertyKey key, Comment comment, MappingNode configuration) {
    String localKey = extractKeysParts(key).getLast();

    parentPropertyNode(key, configuration)
      .getValue()
      .stream()
      .filter(nodeTupleKeyEquals(localKey))
      .map(NodeTuple::getKeyNode)
      .findFirst()
      .ifPresent(keyNode -> keyNode.setBlockComments(commentLines(comment)));
  }

  private List<CommentLine> commentLines(Comment comment) {
    return splitLines(comment)
      .stream()
      .map(commentLine -> new CommentLine(null, null, " " + commentLine, BLOCK))
      .toList();
  }

  private static Collection<String> splitLines(Comment comment) {
    return List.of(comment.get().split("\\r?\\n"));
  }

  private MappingNode parentPropertyNode(PropertyKey key, MappingNode configuration) {
    List<String> allKeys = extractKeysParts(key);
    List<String> parentKeys = allKeys.subList(0, allKeys.size() - 1);

    MappingNode parentMappingNode = configuration;

    for (String partialKey : parentKeys) {
      parentMappingNode = findPartialKeyMappingNode(parentMappingNode, partialKey);
    }

    return parentMappingNode;
  }

  private MappingNode findPartialKeyMappingNode(MappingNode parentMappingNode, String partialKey) {
    return parentMappingNode
      .getValue()
      .stream()
      .filter(nodeTupleKeyEquals(partialKey))
      .map(NodeTuple::getValueNode)
      .findFirst()
      .map(toExistingMappingNode(partialKey))
      .orElseGet(() -> {
        MappingNode newParentConfiguration = newMappingNode();

        parentMappingNode.getValue().add(new NodeTuple(buildScalarNode(partialKey), newParentConfiguration));

        return newParentConfiguration;
      });
  }

  private Function<Node, MappingNode> toExistingMappingNode(String partialKey) {
    return valueNode -> {
      if (!(valueNode instanceof MappingNode mappingNode)) {
        throw GeneratorException.technicalError("Error updating Yaml properties: can't define a subproperty of %s ".formatted(partialKey));
      }

      return mappingNode;
    };
  }

  private MappingNode newMappingNode() {
    return new MappingNode(Tag.MAP, new ArrayList<>(), FlowStyle.AUTO);
  }

  private static Predicate<NodeTuple> nodeTupleKeyEquals(String partialKey) {
    return nodeTuple -> ((ScalarNode) nodeTuple.getKeyNode()).getValue().equals(partialKey);
  }

  private Node buildScalarNode(Object value) {
    Tag tag = buildTag(value);

    return new ScalarNode(tag, value.toString(), null, null, DumperOptions.ScalarStyle.PLAIN);
  }

  private Tag buildTag(Object value) {
    return TAG_BY_JAVA_TYPE.getOrDefault(value.getClass(), Tag.STR);
  }

  private static List<String> extractKeysParts(PropertyKey key) {
    return Arrays.stream(key.get().split("\\.(?![^'\\[]*])"))
      .map(subKey -> subKey.replace("'[", "[").replace("]'", "]"))
      .toList();
  }

  private MappingNode loadConfiguration() throws IOException {
    if (!file.toFile().exists()) {
      return new MappingNode(Tag.MAP, new ArrayList<>(), FlowStyle.AUTO);
    }

    try (Reader reader = Files.newBufferedReader(file, UTF_8)) {
      return (MappingNode) yaml.compose(reader);
    }
  }

  private void saveConfiguration(Node actualConfiguration) throws IOException {
    Files.createDirectories(file.getParent());
    Writer writer = Files.newBufferedWriter(file, UTF_8);
    yaml.serialize(actualConfiguration, writer);
  }

  private Yaml createYaml() {
    LoaderOptions loaderOptions = new LoaderOptions();
    loaderOptions.setProcessComments(true);

    DumperOptions dumperOptions = new DumperOptions();
    dumperOptions.setProcessComments(true);
    dumperOptions.setIndent(indentation.spacesCount());
    dumperOptions.setPrettyFlow(true);
    dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
    dumperOptions.setSplitLines(true);

    return new Yaml(new Constructor(loaderOptions), new Representer(dumperOptions), dumperOptions, loaderOptions);
  }
}
