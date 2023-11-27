package tech.jhipster.lite.module.infrastructure.secondary;

import static org.yaml.snakeyaml.comments.CommentType.BLOCK;
import static org.yaml.snakeyaml.nodes.Tag.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
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
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javaproperties.Comment;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

class YamlFileSpringPropertiesHandler {

  private final Path file;
  private final Indentation indentation;
  private final Yaml yaml;

  public YamlFileSpringPropertiesHandler(Path file, Indentation indentation) {
    this.indentation = indentation;
    Assert.notNull("file", file);

    this.file = file;
    this.yaml = createYaml();
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  public void setValue(PropertyKey key, PropertyValue value) {
    Assert.notNull("key", key);
    Assert.notNull("value", value);

    try {
      MappingNode configuration = loadConfiguration(file.toFile());
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
      MappingNode configuration = loadConfiguration(file.toFile());
      addCommentToConfiguration(key, comment, configuration);
      saveConfiguration(configuration);
    } catch (IOException exception) {
      throw GeneratorException.technicalError("Error updating Yaml properties: " + exception.getMessage(), exception);
    }
  }

  private void appendPropertyToConfiguration(PropertyKey key, PropertyValue value, MappingNode configuration) {
    String localKey = extractKeysParts(key).getLast();

    MappingNode parentConfiguration = parentPropertyNode(key, configuration);
    Node valueNode = value.values().size() > 1
      ? new SequenceNode(SEQ, value.values().stream().map(this::buildScalarNode).toList(), FlowStyle.AUTO)
      : buildScalarNode(value.values().iterator().next());

    parentConfiguration
      .getValue()
      .stream()
      .filter(nodeTupleKeyEquals(localKey))
      .findFirst()
      .ifPresent(existingNodeTuple -> parentConfiguration.getValue().remove(existingNodeTuple));
    parentConfiguration.getValue().add(new NodeTuple(buildScalarNode(localKey), valueNode));
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
    return splitLines(comment).stream().map(commentLine -> new CommentLine(null, null, " " + commentLine, BLOCK)).toList();
  }

  private static Collection<String> splitLines(Comment comment) {
    return List.of(comment.get().split("\\r?\\n"));
  }

  private MappingNode parentPropertyNode(PropertyKey key, MappingNode configuration) {
    List<String> allKeys = extractKeysParts(key);
    List<String> parentKeys = allKeys.subList(0, allKeys.size() - 1);

    MappingNode parentMappingNode = configuration;
    for (String partialKey : parentKeys) {
      Node valueNode = parentMappingNode
        .getValue()
        .stream()
        .filter(nodeTupleKeyEquals(partialKey))
        .map(NodeTuple::getValueNode)
        .findFirst()
        .orElse(null);
      if (valueNode != null && !(valueNode instanceof MappingNode)) {
        throw GeneratorException.technicalError("Error updating Yaml properties: can't define a subproperty of %s ".formatted(partialKey));
      }

      if (valueNode != null) {
        parentMappingNode = (MappingNode) valueNode;
      } else {
        var newParentConfiguration = new MappingNode(MAP, new ArrayList<>(), FlowStyle.AUTO);
        parentMappingNode.getValue().add(new NodeTuple(buildScalarNode(partialKey), newParentConfiguration));
        parentMappingNode = newParentConfiguration;
      }
    }
    return parentMappingNode;
  }

  private static Predicate<NodeTuple> nodeTupleKeyEquals(String partialKey) {
    return nodeTuple -> ((ScalarNode) nodeTuple.getKeyNode()).getValue().equals(partialKey);
  }

  private Node buildScalarNode(Object value) {
    Tag tag = STR;
    if (value instanceof Integer || value instanceof Long) {
      tag = INT;
    } else if (value instanceof Double || value instanceof Float) {
      tag = FLOAT;
    } else if (value instanceof Boolean) {
      tag = BOOL;
    }

    return new ScalarNode(tag, value.toString(), null, null, DumperOptions.ScalarStyle.PLAIN);
  }

  private static List<String> extractKeysParts(PropertyKey key) {
    return Stream.of(key.get().split("\\.(?![^.]*\\]')")).map(subKey -> subKey.replace("'[", "[").replace("]'", "]")).toList();
  }

  private MappingNode loadConfiguration(File yamlFile) throws IOException {
    if (!yamlFile.exists()) {
      return new MappingNode(MAP, new ArrayList<>(), FlowStyle.AUTO);
    }

    try (FileReader reader = new FileReader(yamlFile)) {
      return (MappingNode) yaml.compose(reader);
    }
  }

  private void saveConfiguration(Node actualConfiguration) throws IOException {
    Files.createDirectories(file.getParent());
    Writer writer = new FileWriter(file.toFile());
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
