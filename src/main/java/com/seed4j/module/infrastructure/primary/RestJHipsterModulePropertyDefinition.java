package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.properties.JHipsterPropertyDefaultValue;
import com.seed4j.module.domain.properties.JHipsterPropertyDescription;
import com.seed4j.module.domain.properties.JHipsterPropertyType;
import com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "JHipsterModulePropertyDefinition", description = "Definition for a given property")
final class RestJHipsterModulePropertyDefinition {

  private final JHipsterPropertyType type;
  private final boolean mandatory;
  private final String key;
  private final String description;
  private final String defaultValue;
  private final int order;

  private RestJHipsterModulePropertyDefinition(RestJHipsterModulePropertyDefinitionBuilder builder) {
    type = builder.type;
    mandatory = builder.mandatory;
    key = builder.key;
    description = builder.description;
    defaultValue = builder.defaultValue;
    order = builder.order;
  }

  static RestJHipsterModulePropertyDefinition from(JHipsterModulePropertyDefinition propertyDefinition) {
    return new RestJHipsterModulePropertyDefinitionBuilder()
      .type(propertyDefinition.type())
      .mandatory(propertyDefinition.isMandatory())
      .key(propertyDefinition.key().get())
      .description(propertyDefinition.description().map(JHipsterPropertyDescription::get).orElse(null))
      .defaultValue(propertyDefinition.defaultValue().map(JHipsterPropertyDefaultValue::get).orElse(null))
      .order(propertyDefinition.order())
      .build();
  }

  @Schema(description = "Type of this property", requiredMode = RequiredMode.REQUIRED)
  public JHipsterPropertyType getType() {
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

  private static final class RestJHipsterModulePropertyDefinitionBuilder {

    private JHipsterPropertyType type;
    private boolean mandatory;
    private String key;
    private String description;
    private String defaultValue;
    private int order;

    private RestJHipsterModulePropertyDefinitionBuilder type(JHipsterPropertyType type) {
      this.type = type;

      return this;
    }

    private RestJHipsterModulePropertyDefinitionBuilder mandatory(boolean mandatory) {
      this.mandatory = mandatory;

      return this;
    }

    private RestJHipsterModulePropertyDefinitionBuilder key(String key) {
      this.key = key;

      return this;
    }

    private RestJHipsterModulePropertyDefinitionBuilder description(String description) {
      this.description = description;

      return this;
    }

    private RestJHipsterModulePropertyDefinitionBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;

      return this;
    }

    private RestJHipsterModulePropertyDefinitionBuilder order(int order) {
      this.order = order;

      return this;
    }

    private RestJHipsterModulePropertyDefinition build() {
      return new RestJHipsterModulePropertyDefinition(this);
    }
  }
}
