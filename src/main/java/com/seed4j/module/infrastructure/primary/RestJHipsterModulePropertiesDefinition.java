package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

@Schema(name = "JHipsterModulePropertiesDefinition", description = "Definition for module properties")
final class RestJHipsterModulePropertiesDefinition {

  private final Collection<RestJHipsterModulePropertyDefinition> definitions;

  private RestJHipsterModulePropertiesDefinition(Collection<RestJHipsterModulePropertyDefinition> definitions) {
    this.definitions = definitions;
  }

  static RestJHipsterModulePropertiesDefinition from(JHipsterModulePropertiesDefinition propertiesDefinition) {
    return new RestJHipsterModulePropertiesDefinition(
      propertiesDefinition.stream().map(RestJHipsterModulePropertyDefinition::from).toList()
    );
  }

  @Schema(description = "Definition of the properties for a module")
  public Collection<RestJHipsterModulePropertyDefinition> getDefinitions() {
    return definitions;
  }
}
