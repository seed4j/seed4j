package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.properties.Seed4JPropertyDefaultValue;
import com.seed4j.module.domain.properties.Seed4JPropertyDescription;
import com.seed4j.module.domain.properties.Seed4JPropertyType;
import com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "Seed4JModulePropertyDefinition", description = "Definition for a given property")
final class RestSeed4JModulePropertyDefinition {

  private final Seed4JPropertyType type;
  private final boolean mandatory;
  private final String key;
  private final String description;
  private final String defaultValue;
  private final int order;

  private RestSeed4JModulePropertyDefinition(RestSeed4JModulePropertyDefinitionBuilder builder) {
    type = builder.type;
    mandatory = builder.mandatory;
    key = builder.key;
    description = builder.description;
    defaultValue = builder.defaultValue;
    order = builder.order;
  }

  static RestSeed4JModulePropertyDefinition from(Seed4JModulePropertyDefinition propertyDefinition) {
    return new RestSeed4JModulePropertyDefinitionBuilder()
      .type(propertyDefinition.type())
      .mandatory(propertyDefinition.isMandatory())
      .key(propertyDefinition.key().get())
      .description(propertyDefinition.description().map(Seed4JPropertyDescription::get).orElse(null))
      .defaultValue(propertyDefinition.defaultValue().map(Seed4JPropertyDefaultValue::get).orElse(null))
      .order(propertyDefinition.order())
      .build();
  }

  @Schema(description = "Type of this property", requiredMode = RequiredMode.REQUIRED)
  public Seed4JPropertyType getType() {
    return type;
  }

  @Schema(description = "True if this property is mandatory, false otherwise", requiredMode = RequiredMode.REQUIRED)
  public boolean isMandatory() {
    return mandatory;
  }

  @Schema(description = "Key of this property", requiredMode = RequiredMode.REQUIRED)
  public String getKey() {
    return key;
  }

  @Schema(description = "Full text description of this property")
  public String getDescription() {
    return description;
  }

  @Schema(description = "Default value for this property")
  public String getDefaultValue() {
    return defaultValue;
  }

  @Schema(description = "Order (sort in natural int sorting) for this property", requiredMode = RequiredMode.REQUIRED)
  public int getOrder() {
    return order;
  }

  private static final class RestSeed4JModulePropertyDefinitionBuilder {

    private Seed4JPropertyType type;
    private boolean mandatory;
    private String key;
    private String description;
    private String defaultValue;
    private int order;

    private RestSeed4JModulePropertyDefinitionBuilder type(Seed4JPropertyType type) {
      this.type = type;

      return this;
    }

    private RestSeed4JModulePropertyDefinitionBuilder mandatory(boolean mandatory) {
      this.mandatory = mandatory;

      return this;
    }

    private RestSeed4JModulePropertyDefinitionBuilder key(String key) {
      this.key = key;

      return this;
    }

    private RestSeed4JModulePropertyDefinitionBuilder description(String description) {
      this.description = description;

      return this;
    }

    private RestSeed4JModulePropertyDefinitionBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;

      return this;
    }

    private RestSeed4JModulePropertyDefinitionBuilder order(int order) {
      this.order = order;

      return this;
    }

    private RestSeed4JModulePropertyDefinition build() {
      return new RestSeed4JModulePropertyDefinition(this);
    }
  }
}
