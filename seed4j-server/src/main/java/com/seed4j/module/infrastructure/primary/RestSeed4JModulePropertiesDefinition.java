package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

@Schema(name = "Seed4JModulePropertiesDefinition", description = "Definition for module properties")
final class RestSeed4JModulePropertiesDefinition {

  private final Collection<RestSeed4JModulePropertyDefinition> definitions;

  private RestSeed4JModulePropertiesDefinition(Collection<RestSeed4JModulePropertyDefinition> definitions) {
    this.definitions = definitions;
  }

  static RestSeed4JModulePropertiesDefinition from(Seed4JModulePropertiesDefinition propertiesDefinition) {
    return new RestSeed4JModulePropertiesDefinition(propertiesDefinition.stream().map(RestSeed4JModulePropertyDefinition::from).toList());
  }

  @Schema(description = "Definition of the properties for a module")
  public Collection<RestSeed4JModulePropertyDefinition> getDefinitions() {
    return definitions;
  }
}
