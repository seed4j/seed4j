package com.seed4j.module.infrastructure.secondary;

import static java.util.stream.Collectors.joining;

import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PropertiesFileSpringFactoriesHandler {

  private static final String EQUAL = "=";
  private static final String COLLECTION_SEPARATOR = ",";
  private static final String LINE_BREAK = System.lineSeparator();

  private final Path file;

  public PropertiesFileSpringFactoriesHandler(Path file) {
    Assert.notNull("file", file);

    this.file = file;
  }

  public void append(PropertyKey key, PropertyValue value) {
    Assert.notNull("key", key);
    Assert.notNull("value", value);

    updateFactories(key, value);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  private void updateFactories(PropertyKey key, PropertyValue value) {
    try {
      String properties = buildFactories(key, value);

      Files.writeString(file, properties);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error updating Spring Factories properties: " + e.getMessage(), e);
    }
  }

  private String buildFactories(PropertyKey key, PropertyValue value) throws IOException {
    String currentProperties = readOrInitFactories();

    int propertyIndex = currentProperties.indexOf(propertyId(key));
    if (propertyIndex != -1) {
      return appendValuesToExistingPropertyKey(propertyIndex, value, currentProperties);
    }
    return addNewFactory(key, value, currentProperties);
  }

  private String addNewFactory(PropertyKey key, PropertyValue value, String currentProperties) {
    return currentProperties + propertyLine(key, value) + LINE_BREAK;
  }

  private static String appendValuesToExistingPropertyKey(int propertyIndex, PropertyValue value, String currentProperties) {
    StringBuilder newProperties = new StringBuilder(currentProperties);
    int eolIndex = newProperties.indexOf(LINE_BREAK, propertyIndex);

    for (Object propertyValue : value.get()) {
      if (!newProperties.substring(propertyIndex, eolIndex).contains(propertyValue.toString())) {
        newProperties.insert(eolIndex, COLLECTION_SEPARATOR + propertyValue);
        eolIndex = eolIndex + COLLECTION_SEPARATOR.length() + propertyValue.toString().length();
      }
    }
    return newProperties.toString();
  }

  private String readOrInitFactories() throws IOException {
    if (Files.notExists(file)) {
      Files.createDirectories(file.getParent());
      Files.createFile(file);

      return "";
    }

    return Files.readString(file);
  }

  private String propertyLine(PropertyKey key, PropertyValue value) {
    return propertyId(key) + joinedPropertyValues(value);
  }

  private static String joinedPropertyValues(PropertyValue value) {
    return value.get().stream().map(Object::toString).collect(joining(COLLECTION_SEPARATOR));
  }

  private String propertyId(PropertyKey key) {
    return key.get() + EQUAL;
  }
}
