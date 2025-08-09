package com.seed4j.module.domain.resource;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.module.domain.properties.*;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SeedModulePropertyDefinition {

  private final SeedPropertyType type;
  private final boolean mandatory;
  private final SeedPropertyKey key;
  private final Optional<SeedPropertyDescription> description;
  private final Optional<SeedPropertyDefaultValue> defaultValue;
  private final int order;

  private SeedModulePropertyDefinition(SeedModulePropertyDefinitionBuilder builder) {
    Assert.notNull("type", builder.type);
    Assert.notBlank("key", builder.key);

    type = builder.type;
    mandatory = builder.mandatory;
    key = new SeedPropertyKey(builder.key);
    description = SeedPropertyDescription.of(builder.description);
    defaultValue = SeedPropertyDefaultValue.of(builder.defaultValue);
    order = builder.order;
  }

  public static SeedModulePropertyDefinition basePackageProperty() {
    return mandatoryStringProperty(SeedModuleProperties.BASE_PACKAGE_PARAMETER)
      .description("Base java package")
      .defaultValue("com.mycompany.myapp")
      .order(-300)
      .build();
  }

  public static SeedModulePropertyDefinition projectNameProperty() {
    return mandatoryStringProperty(SeedModuleProperties.PROJECT_NAME_PARAMETER)
      .description("Project full name")
      .defaultValue("JHipster Sample Application")
      .order(-200)
      .build();
  }

  static SeedModulePropertyDefinition projectBaseNameProperty() {
    return mandatoryStringProperty(SeedModuleProperties.PROJECT_BASE_NAME_PARAMETER)
      .description("Project short name (only letters and numbers)")
      .defaultValue("jhipsterSampleApplication")
      .order(-100)
      .build();
  }

  static SeedModulePropertyDefinition nodePackageManagerProperty() {
    return mandatoryStringProperty(SeedModuleProperties.NODE_PACKAGE_MANAGER)
      .description("Node package manager")
      .defaultValue("npm")
      .order(-100)
      .build();
  }

  static SeedModulePropertyDefinition serverPortProperty() {
    return mandatoryIntegerProperty(SeedModuleProperties.SERVER_PORT_PARAMETER)
      .description("Server port")
      .defaultValue("8080")
      .order(-50)
      .build();
  }

  public static SeedModulePropertyDefinition endOfLineProperty() {
    return optionalStringProperty("endOfLine").description("Type of line break (lf or crlf)").defaultValue("lf").order(100).build();
  }

  public static SeedModulePropertyDefinition indentationProperty() {
    return optionalIntegerProperty(SeedModuleProperties.INDENTATION_PARAMETER)
      .description("Number of spaces in indentation")
      .defaultValue("2")
      .order(500)
      .build();
  }

  public static SeedModulePropertyDefinition springConfigurationFormatProperty() {
    return optionalStringProperty(SeedModuleProperties.SPRING_CONFIGURATION_FORMAT)
      .description("Format of the Spring configuration files (yaml or properties)")
      .defaultValue("yaml")
      .order(500)
      .build();
  }

  public static SeedModulePropertyDefinitionOptionalFieldsBuilder mandatoryStringProperty(String key) {
    return builder().type(SeedPropertyType.STRING).mandatory(true).key(key);
  }

  public static SeedModulePropertyDefinitionOptionalFieldsBuilder optionalStringProperty(String key) {
    return builder().type(SeedPropertyType.STRING).mandatory(false).key(key);
  }

  public static SeedModulePropertyDefinitionOptionalFieldsBuilder mandatoryIntegerProperty(String key) {
    return builder().type(SeedPropertyType.INTEGER).mandatory(true).key(key);
  }

  public static SeedModulePropertyDefinitionOptionalFieldsBuilder optionalIntegerProperty(String key) {
    return builder().type(SeedPropertyType.INTEGER).mandatory(false).key(key);
  }

  public static SeedModulePropertyDefinitionOptionalFieldsBuilder mandatoryBooleanProperty(String key) {
    return builder().type(SeedPropertyType.BOOLEAN).mandatory(true).key(key);
  }

  public static SeedModulePropertyDefinitionOptionalFieldsBuilder optionalBooleanProperty(String key) {
    return builder().type(SeedPropertyType.BOOLEAN).mandatory(false).key(key);
  }

  public static SeedModulePropertyDefinitionTypeBuilder builder() {
    return new SeedModulePropertyDefinitionBuilder();
  }

  public SeedPropertyType type() {
    return type;
  }

  public SeedPropertyKey key() {
    return key;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public Optional<SeedPropertyDescription> description() {
    return description;
  }

  public Optional<SeedPropertyDefaultValue> defaultValue() {
    return defaultValue;
  }

  public int order() {
    return order;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("type", type)
      .append("key", key)
      .append("mandatory", mandatory)
      .append("description", description.map(SeedPropertyDescription::get).orElse(""))
      .append("defaultValue", defaultValue.map(SeedPropertyDefaultValue::get).orElse(""))
      .append("order", order)
      .build();
  }

  private static final class SeedModulePropertyDefinitionBuilder
    implements
      SeedModulePropertyDefinitionTypeBuilder,
      SeedModulePropertyDefinitionOptionalityBuilder,
      SeedModulePropertyDefinitionKeyBuilder,
      SeedModulePropertyDefinitionOptionalFieldsBuilder {

    private SeedPropertyType type;
    private boolean mandatory;
    private String key;
    private String description;
    private String defaultValue;
    private int order;

    @Override
    public SeedModulePropertyDefinitionOptionalityBuilder type(SeedPropertyType type) {
      this.type = type;

      return this;
    }

    @Override
    public SeedModulePropertyDefinitionKeyBuilder mandatory(boolean mandatory) {
      this.mandatory = mandatory;

      return this;
    }

    @Override
    public SeedModulePropertyDefinitionOptionalFieldsBuilder key(String key) {
      this.key = key;

      return this;
    }

    @Override
    public SeedModulePropertyDefinitionOptionalFieldsBuilder description(String description) {
      this.description = description;

      return this;
    }

    @Override
    public SeedModulePropertyDefinitionOptionalFieldsBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;

      return this;
    }

    @Override
    public SeedModulePropertyDefinitionOptionalFieldsBuilder order(int order) {
      this.order = order;

      return this;
    }

    @Override
    public SeedModulePropertyDefinition build() {
      return new SeedModulePropertyDefinition(this);
    }
  }

  public interface SeedModulePropertyDefinitionTypeBuilder {
    SeedModulePropertyDefinitionOptionalityBuilder type(SeedPropertyType type);
  }

  public interface SeedModulePropertyDefinitionOptionalityBuilder {
    SeedModulePropertyDefinitionKeyBuilder mandatory(boolean mandatory);
  }

  public interface SeedModulePropertyDefinitionKeyBuilder {
    SeedModulePropertyDefinitionOptionalFieldsBuilder key(String key);
  }

  public interface SeedModulePropertyDefinitionOptionalFieldsBuilder {
    SeedModulePropertyDefinitionOptionalFieldsBuilder description(String description);

    SeedModulePropertyDefinitionOptionalFieldsBuilder defaultValue(String defaultValue);

    SeedModulePropertyDefinitionOptionalFieldsBuilder order(int order);

    SeedModulePropertyDefinition build();
  }
}
