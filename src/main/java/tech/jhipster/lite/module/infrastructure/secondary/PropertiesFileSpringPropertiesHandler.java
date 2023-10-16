package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

class PropertiesFileSpringPropertiesHandler {

  private static final String EQUAL = "=";
  private static final String COLLECTION_SEPARATOR = ",";

  private final Path file;

  public PropertiesFileSpringPropertiesHandler(Path file) {
    Assert.notNull("file", file);

    this.file = file;
  }

  public void set(PropertyKey key, PropertyValue value) {
    Assert.notNull("key", key);
    Assert.notNull("value", value);

    updateProperties(key, value);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  private void updateProperties(PropertyKey key, PropertyValue value) {
    try {
      String properties = buildProperties(key, value);

      Files.write(file, properties.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error updating properties: " + e.getMessage(), e);
    }
  }

  private String buildProperties(PropertyKey key, PropertyValue value) throws IOException {
    String currentProperties = readOrInitProperties();

    int propertyIndex = currentProperties.indexOf(propertyId(key));
    if (propertyIndex != -1) {
      String start = currentProperties.substring(0, propertyIndex);
      String end = currentProperties.substring(currentProperties.indexOf(LINE_BREAK, propertyIndex));

      return start + propertyLine(key, value) + end;
    }

    return currentProperties + propertyLine(key, value);
  }

  private String readOrInitProperties() throws IOException {
    if (Files.notExists(file)) {
      Files.createDirectories(file.getParent());
      Files.createFile(file);

      return "";
    }

    return Files.readString(file) + LINE_BREAK;
  }

  private String propertyLine(PropertyKey key, PropertyValue value) {
    return new StringBuilder()
      .append(propertyId(key))
      .append(value.get().stream().map(Object::toString).collect(Collectors.joining(COLLECTION_SEPARATOR)))
      .toString();
  }

  private String propertyId(PropertyKey key) {
    return key.get() + EQUAL;
  }
}
