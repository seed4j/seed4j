package com.seed4j.module.domain.resource;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.properties.Seed4JPropertyDefaultValue;
import com.seed4j.module.domain.properties.Seed4JPropertyDescription;
import com.seed4j.module.domain.properties.Seed4JPropertyKey;
import com.seed4j.module.domain.properties.Seed4JPropertyType;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jspecify.annotations.Nullable;

public final class Seed4JModulePropertyDefinition {

  private final Seed4JPropertyType type;
  private final boolean mandatory;
  private final Seed4JPropertyKey key;
  private final Optional<Seed4JPropertyDescription> description;
  private final Optional<Seed4JPropertyDefaultValue> defaultValue;
  private final int order;

  private Seed4JModulePropertyDefinition(Seed4JModulePropertyDefinitionBuilder builder) {
    Assert.notNull("type", builder.type);
    Assert.notBlank("key", builder.key);

    type = builder.type;
    mandatory = builder.mandatory;
    key = new Seed4JPropertyKey(builder.key);
    description = Seed4JPropertyDescription.of(builder.description);
    defaultValue = Seed4JPropertyDefaultValue.of(builder.defaultValue);
    order = builder.order;
  }

  public static Seed4JModulePropertyDefinition basePackageProperty() {
    return mandatoryStringProperty(Seed4JModuleProperties.BASE_PACKAGE_PARAMETER)
      .description("Base java package")
      .defaultValue("com.mycompany.myapp")
      .order(-300)
      .build();
  }

  public static Seed4JModulePropertyDefinition projectNameProperty() {
    return mandatoryStringProperty(Seed4JModuleProperties.PROJECT_NAME_PARAMETER)
      .description("Project full name")
      .defaultValue("Seed4J Sample Application")
      .order(-200)
      .build();
  }

  static Seed4JModulePropertyDefinition projectBaseNameProperty() {
    return mandatoryStringProperty(Seed4JModuleProperties.PROJECT_BASE_NAME_PARAMETER)
      .description("Project short name (only letters and numbers)")
      .defaultValue("seed4jSampleApplication")
      .order(-100)
      .build();
  }

  static Seed4JModulePropertyDefinition nodePackageManagerProperty() {
    return mandatoryStringProperty(Seed4JModuleProperties.NODE_PACKAGE_MANAGER)
      .description("Node package manager")
      .defaultValue("npm")
      .order(-100)
      .build();
  }

  static Seed4JModulePropertyDefinition serverPortProperty() {
    return mandatoryIntegerProperty(Seed4JModuleProperties.SERVER_PORT_PARAMETER)
      .description("Server port")
      .defaultValue("8080")
      .order(-50)
      .build();
  }

  public static Seed4JModulePropertyDefinition endOfLineProperty() {
    return optionalStringProperty("endOfLine").description("Type of line break (lf or crlf)").defaultValue("lf").order(100).build();
  }

  public static Seed4JModulePropertyDefinition indentationProperty() {
    return optionalIntegerProperty(Seed4JModuleProperties.INDENTATION_PARAMETER)
      .description("Number of spaces in indentation")
      .defaultValue("2")
      .order(500)
      .build();
  }

  public static Seed4JModulePropertyDefinition springConfigurationFormatProperty() {
    return optionalStringProperty(Seed4JModuleProperties.SPRING_CONFIGURATION_FORMAT)
      .description("Format of the Spring configuration files (yaml or properties)")
      .defaultValue("yaml")
      .order(500)
      .build();
  }

  public static Seed4JModulePropertyDefinitionOptionalFieldsBuilder mandatoryStringProperty(String key) {
    return builder().type(Seed4JPropertyType.STRING).mandatory(true).key(key);
  }

  public static Seed4JModulePropertyDefinitionOptionalFieldsBuilder optionalStringProperty(String key) {
    return builder().type(Seed4JPropertyType.STRING).mandatory(false).key(key);
  }

  public static Seed4JModulePropertyDefinitionOptionalFieldsBuilder mandatoryIntegerProperty(String key) {
    return builder().type(Seed4JPropertyType.INTEGER).mandatory(true).key(key);
  }

  public static Seed4JModulePropertyDefinitionOptionalFieldsBuilder optionalIntegerProperty(String key) {
    return builder().type(Seed4JPropertyType.INTEGER).mandatory(false).key(key);
  }

  public static Seed4JModulePropertyDefinitionOptionalFieldsBuilder mandatoryBooleanProperty(String key) {
    return builder().type(Seed4JPropertyType.BOOLEAN).mandatory(true).key(key);
  }

  public static Seed4JModulePropertyDefinitionOptionalFieldsBuilder optionalBooleanProperty(String key) {
    return builder().type(Seed4JPropertyType.BOOLEAN).mandatory(false).key(key);
  }

  public static Seed4JModulePropertyDefinitionTypeBuilder builder() {
    return new Seed4JModulePropertyDefinitionBuilder();
  }

  public Seed4JPropertyType type() {
    return type;
  }

  public Seed4JPropertyKey key() {
    return key;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public Optional<Seed4JPropertyDescription> description() {
    return description;
  }

  public Optional<Seed4JPropertyDefaultValue> defaultValue() {
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
      .append("description", description.map(Seed4JPropertyDescription::get).orElse(""))
      .append("defaultValue", defaultValue.map(Seed4JPropertyDefaultValue::get).orElse(""))
      .append("order", order)
      .build();
  }

  private static final class Seed4JModulePropertyDefinitionBuilder
    implements
      Seed4JModulePropertyDefinitionTypeBuilder,
      Seed4JModulePropertyDefinitionOptionalityBuilder,
      Seed4JModulePropertyDefinitionKeyBuilder,
      Seed4JModulePropertyDefinitionOptionalFieldsBuilder
  {

    private @Nullable Seed4JPropertyType type;
    private boolean mandatory;
    private @Nullable String key;
    private @Nullable String description;
    private @Nullable String defaultValue;
    private int order;

    @Override
    public Seed4JModulePropertyDefinitionOptionalityBuilder type(Seed4JPropertyType type) {
      this.type = type;

      return this;
    }

    @Override
    public Seed4JModulePropertyDefinitionKeyBuilder mandatory(boolean mandatory) {
      this.mandatory = mandatory;

      return this;
    }

    @Override
    public Seed4JModulePropertyDefinitionOptionalFieldsBuilder key(String key) {
      this.key = key;

      return this;
    }

    @Override
    public Seed4JModulePropertyDefinitionOptionalFieldsBuilder description(String description) {
      this.description = description;

      return this;
    }

    @Override
    public Seed4JModulePropertyDefinitionOptionalFieldsBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;

      return this;
    }

    @Override
    public Seed4JModulePropertyDefinitionOptionalFieldsBuilder order(int order) {
      this.order = order;

      return this;
    }

    @Override
    public Seed4JModulePropertyDefinition build() {
      return new Seed4JModulePropertyDefinition(this);
    }
  }

  public interface Seed4JModulePropertyDefinitionTypeBuilder {
    Seed4JModulePropertyDefinitionOptionalityBuilder type(Seed4JPropertyType type);
  }

  public interface Seed4JModulePropertyDefinitionOptionalityBuilder {
    Seed4JModulePropertyDefinitionKeyBuilder mandatory(boolean mandatory);
  }

  public interface Seed4JModulePropertyDefinitionKeyBuilder {
    Seed4JModulePropertyDefinitionOptionalFieldsBuilder key(String key);
  }

  public interface Seed4JModulePropertyDefinitionOptionalFieldsBuilder {
    Seed4JModulePropertyDefinitionOptionalFieldsBuilder description(String description);

    Seed4JModulePropertyDefinitionOptionalFieldsBuilder defaultValue(String defaultValue);

    Seed4JModulePropertyDefinitionOptionalFieldsBuilder order(int order);

    Seed4JModulePropertyDefinition build();
  }
}
