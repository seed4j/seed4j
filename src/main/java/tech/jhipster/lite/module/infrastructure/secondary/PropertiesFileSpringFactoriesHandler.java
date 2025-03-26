package tech.jhipster.lite.module.infrastructure.secondary;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public class PropertiesFileSpringFactoriesHandler extends AbstractSpringFactoriesHandler {

  private static final String COLLECTION_SEPARATOR = ",";
  private static final String LINE_BREAK = System.lineSeparator();

  private final SpringFactoriesFileManager fileManager;

  public PropertiesFileSpringFactoriesHandler(SpringFactoriesFileManager fileManager) {
    Assert.notNull("fileManager", fileManager);
    this.fileManager = fileManager;
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  protected void updateFactories(PropertyKey key, PropertyValue value) {
    try {
      String properties = buildFactories(key, value);
      fileManager.write(properties);
    } catch (IOException e) {
      throw new IllegalStateException("Error updating Spring Factories properties: " + e.getMessage(), e);
    }
  }

  private String buildFactories(PropertyKey key, PropertyValue value) throws IOException {
    String currentProperties = fileManager.readOrInit();

    int propertyIndex = currentProperties.indexOf(SpringFactoriesUtils.propertyId(key));
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

  private String propertyLine(PropertyKey key, PropertyValue value) {
    return SpringFactoriesUtils.propertyId(key) + joinedPropertyValues(value);
  }

  private static String joinedPropertyValues(PropertyValue value) {
    return value.get().stream().map(Object::toString).collect(joining(COLLECTION_SEPARATOR));
  }
}
