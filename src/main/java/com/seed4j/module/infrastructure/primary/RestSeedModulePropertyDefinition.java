package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.properties.SeedPropertyDefaultValue;
import com.seed4j.module.domain.properties.SeedPropertyDescription;
import com.seed4j.module.domain.properties.SeedPropertyType;
import com.seed4j.module.domain.resource.SeedModulePropertyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "SeedModulePropertyDefinition", description = "Definition for a given property")
final class RestSeedModulePropertyDefinition {

  private final SeedPropertyType type;
  private final boolean mandatory;
  private final String key;
  private final String description;
  private final String defaultValue;
  private final int order;

  private RestSeedModulePropertyDefinition(RestSeedModulePropertyDefinitionBuilder builder) {
    type = builder.type;
    mandatory = builder.mandatory;
    key = builder.key;
    description = builder.description;
    defaultValue = builder.defaultValue;
    order = builder.order;
  }

  static RestSeedModulePropertyDefinition from(SeedModulePropertyDefinition propertyDefinition) {
    return new RestSeedModulePropertyDefinitionBuilder()
      .type(propertyDefinition.type())
      .mandatory(propertyDefinition.isMandatory())
      .key(propertyDefinition.key().get())
      .description(propertyDefinition.description().map(SeedPropertyDescription::get).orElse(null))
      .defaultValue(propertyDefinition.defaultValue().map(SeedPropertyDefaultValue::get).orElse(null))
      .order(propertyDefinition.order())
      .build();
  }

  @Schema(description = "Type of this property", requiredMode = RequiredMode.REQUIRED)
  public SeedPropertyType getType() {
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

  private static final class RestSeedModulePropertyDefinitionBuilder {

    private SeedPropertyType type;
    private boolean mandatory;
    private String key;
    private String description;
    private String defaultValue;
    private int order;

    private RestSeedModulePropertyDefinitionBuilder type(SeedPropertyType type) {
      this.type = type;

      return this;
    }

    private RestSeedModulePropertyDefinitionBuilder mandatory(boolean mandatory) {
      this.mandatory = mandatory;

      return this;
    }

    private RestSeedModulePropertyDefinitionBuilder key(String key) {
      this.key = key;

      return this;
    }

    private RestSeedModulePropertyDefinitionBuilder description(String description) {
      this.description = description;

      return this;
    }

    private RestSeedModulePropertyDefinitionBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;

      return this;
    }

    private RestSeedModulePropertyDefinitionBuilder order(int order) {
      this.order = order;

      return this;
    }

    private RestSeedModulePropertyDefinition build() {
      return new RestSeedModulePropertyDefinition(this);
    }
  }
}
