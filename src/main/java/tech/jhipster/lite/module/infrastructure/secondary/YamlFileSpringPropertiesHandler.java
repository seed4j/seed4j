package tech.jhipster.lite.module.infrastructure.secondary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import tech.jhipster.lite.module.domain.Indentation;
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

  public void set(PropertyKey key, PropertyValue value) {
    Assert.notNull("key", key);
    Assert.notNull("value", value);

    updateProperties(key, value);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  private void updateProperties(PropertyKey key, PropertyValue value) {
    Map<String, Object> configuration = null;
    try {
      configuration = loadConfiguration(file.toFile());
      appendPropertyToConfiguration(key, value, configuration);
      saveConfiguration(configuration);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error updating Yaml properties: " + e.getMessage(), e);
    }
  }

  @SuppressWarnings("unchecked")
  private static void appendPropertyToConfiguration(PropertyKey key, PropertyValue value, Map<String, Object> configuration) {
    String[] allKeys = extractKeysParts(key);
    String[] parentKeys = Arrays.copyOfRange(allKeys, 0, allKeys.length - 1);
    String localKey = allKeys[allKeys.length - 1];

    Map<String, Object> parentMap = configuration;
    for (String partialKey : parentKeys) {
      parentMap.computeIfAbsent(partialKey, s -> new TreeMap<>());
      if (Map.class.isAssignableFrom(parentMap.get(partialKey).getClass())) {
        parentMap = (Map<String, Object>) parentMap.get(partialKey);
      } else {
        throw GeneratorException.technicalError("Error updating Yaml properties: can't define a subproperty of %s ".formatted(partialKey));
      }
    }
    parentMap.put(localKey, extractValue(value));
  }

  private static String[] extractKeysParts(PropertyKey key) {
    return Stream
      .of(key.get().split("\\.(?![^.]*\\]')"))
      .map(subKey -> subKey.replace("'[", "[").replace("]'", "]"))
      .toArray(String[]::new);
  }

  private static Object extractValue(PropertyValue value) {
    if (value.values().size() == 1) {
      return value.values().iterator().next();
    }
    return value.values().toArray();
  }

  private Map<String, Object> loadConfiguration(File yamlFile) throws FileNotFoundException {
    if (!yamlFile.exists()) {
      return new HashMap<>();
    }

    return yaml.load(new FileInputStream(yamlFile));
  }

  private void saveConfiguration(Map<String, Object> actualConfiguration) throws IOException {
    Files.createDirectories(file.getParent());
    Writer writer = new FileWriter(file.toFile());
    yaml.dump(actualConfiguration, writer);
  }

  private Yaml createYaml() {
    DumperOptions dumperOptions = new DumperOptions();
    dumperOptions.setIndent(indentation.spacesCount());
    dumperOptions.setPrettyFlow(true);
    dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
    dumperOptions.setSplitLines(true);
    return new Yaml(dumperOptions);
  }
}
