package com.seed4j.module.domain.resource;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.module.domain.properties.*;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class JHipsterModulePropertyDefinition {

  private final SeedPropertyType type;
  private final boolean mandatory;
  private final SeedPropertyKey key;
  private final Optional<SeedPropertyDescription> description;
  private final Optional<SeedPropertyDefaultValue> defaultValue;
  private final int order;

  private JHipsterModulePropertyDefinition(JHipsterModulePropertyDefinitionBuilder builder) {
    Assert.notNull("type", builder.type);
    Assert.notBlank("key", builder.key);

    type = builder.type;
    mandatory = builder.mandatory;
    key = new SeedPropertyKey(builder.key);
    description = SeedPropertyDescription.of(builder.description);
    defaultValue = SeedPropertyDefaultValue.of(builder.defaultValue);
    order = builder.order;
  }

  public static JHipsterModulePropertyDefinition basePackageProperty() {
    return mandatoryStringProperty(SeedModuleProperties.BASE_PACKAGE_PARAMETER)
      .description("Base java package")
      .defaultValue("com.mycompany.myapp")
      .order(-300)
      .build();
  }

  public static JHipsterModulePropertyDefinition projectNameProperty() {
    return mandatoryStringProperty(SeedModuleProperties.PROJECT_NAME_PARAMETER)
      .description("Project full name")
      .defaultValue("JHipster Sample Application")
      .order(-200)
      .build();
  }

  static JHipsterModulePropertyDefinition projectBaseNameProperty() {
    return mandatoryStringProperty(SeedModuleProperties.PROJECT_BASE_NAME_PARAMETER)
      .description("Project short name (only letters and numbers)")
      .defaultValue("jhipsterSampleApplication")
      .order(-100)
      .build();
  }

  static JHipsterModulePropertyDefinition nodePackageManagerProperty() {
    return mandatoryStringProperty(SeedModuleProperties.NODE_PACKAGE_MANAGER)
      .description("Node package manager")
      .defaultValue("npm")
      .order(-100)
      .build();
  }

  static JHipsterModulePropertyDefinition serverPortProperty() {
    return mandatoryIntegerProperty(SeedModuleProperties.SERVER_PORT_PARAMETER)
      .description("Server port")
      .defaultValue("8080")
      .order(-50)
      .build();
  }

  public static JHipsterModulePropertyDefinition endOfLineProperty() {
    return optionalStringProperty("endOfLine").description("Type of line break (lf or crlf)").defaultValue("lf").order(100).build();
  }

  public static JHipsterModulePropertyDefinition indentationProperty() {
    return optionalIntegerProperty(SeedModuleProperties.INDENTATION_PARAMETER)
      .description("Number of spaces in indentation")
      .defaultValue("2")
      .order(500)
      .build();
  }

  public static JHipsterModulePropertyDefinition springConfigurationFormatProperty() {
    return optionalStringProperty(SeedModuleProperties.SPRING_CONFIGURATION_FORMAT)
      .description("Format of the Spring configuration files (yaml or properties)")
      .defaultValue("yaml")
      .order(500)
      .build();
  }

  public static JHipsterModulePropertyDefinitionOptionalFieldsBuilder mandatoryStringProperty(String key) {
    return builder().type(SeedPropertyType.STRING).mandatory(true).key(key);
  }

  public static JHipsterModulePropertyDefinitionOptionalFieldsBuilder optionalStringProperty(String key) {
    return builder().type(SeedPropertyType.STRING).mandatory(false).key(key);
  }

  public static JHipsterModulePropertyDefinitionOptionalFieldsBuilder mandatoryIntegerProperty(String key) {
    return builder().type(SeedPropertyType.INTEGER).mandatory(true).key(key);
  }

  public static JHipsterModulePropertyDefinitionOptionalFieldsBuilder optionalIntegerProperty(String key) {
    return builder().type(SeedPropertyType.INTEGER).mandatory(false).key(key);
  }

  public static JHipsterModulePropertyDefinitionOptionalFieldsBuilder mandatoryBooleanProperty(String key) {
    return builder().type(SeedPropertyType.BOOLEAN).mandatory(true).key(key);
  }

  public static JHipsterModulePropertyDefinitionOptionalFieldsBuilder optionalBooleanProperty(String key) {
    return builder().type(SeedPropertyType.BOOLEAN).mandatory(false).key(key);
  }

  public static JHipsterModulePropertyDefinitionTypeBuilder builder() {
    return new JHipsterModulePropertyDefinitionBuilder();
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

  private static final class JHipsterModulePropertyDefinitionBuilder
    implements
      JHipsterModulePropertyDefinitionTypeBuilder,
      JHipsterModulePropertyDefinitionOptionalityBuilder,
      JHipsterModulePropertyDefinitionKeyBuilder,
      JHipsterModulePropertyDefinitionOptionalFieldsBuilder {

    private SeedPropertyType type;
    private boolean mandatory;
    private String key;
    private String description;
    private String defaultValue;
    private int order;

    @Override
    public JHipsterModulePropertyDefinitionOptionalityBuilder type(SeedPropertyType type) {
      this.type = type;

      return this;
    }

    @Override
    public JHipsterModulePropertyDefinitionKeyBuilder mandatory(boolean mandatory) {
      this.mandatory = mandatory;

      return this;
    }

    @Override
    public JHipsterModulePropertyDefinitionOptionalFieldsBuilder key(String key) {
      this.key = key;

      return this;
    }

    @Override
    public JHipsterModulePropertyDefinitionOptionalFieldsBuilder description(String description) {
      this.description = description;

      return this;
    }

    @Override
    public JHipsterModulePropertyDefinitionOptionalFieldsBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;

      return this;
    }

    @Override
    public JHipsterModulePropertyDefinitionOptionalFieldsBuilder order(int order) {
      this.order = order;

      return this;
    }

    @Override
    public JHipsterModulePropertyDefinition build() {
      return new JHipsterModulePropertyDefinition(this);
    }
  }

  public interface JHipsterModulePropertyDefinitionTypeBuilder {
    JHipsterModulePropertyDefinitionOptionalityBuilder type(SeedPropertyType type);
  }

  public interface JHipsterModulePropertyDefinitionOptionalityBuilder {
    JHipsterModulePropertyDefinitionKeyBuilder mandatory(boolean mandatory);
  }

  public interface JHipsterModulePropertyDefinitionKeyBuilder {
    JHipsterModulePropertyDefinitionOptionalFieldsBuilder key(String key);
  }

  public interface JHipsterModulePropertyDefinitionOptionalFieldsBuilder {
    JHipsterModulePropertyDefinitionOptionalFieldsBuilder description(String description);

    JHipsterModulePropertyDefinitionOptionalFieldsBuilder defaultValue(String defaultValue);

    JHipsterModulePropertyDefinitionOptionalFieldsBuilder order(int order);

    JHipsterModulePropertyDefinition build();
  }
}
