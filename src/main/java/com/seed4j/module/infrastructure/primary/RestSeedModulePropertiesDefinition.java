package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

@Schema(name = "SeedModulePropertiesDefinition", description = "Definition for module properties")
final class RestSeedModulePropertiesDefinition {

  private final Collection<RestSeedModulePropertyDefinition> definitions;

  private RestSeedModulePropertiesDefinition(Collection<RestSeedModulePropertyDefinition> definitions) {
    this.definitions = definitions;
  }

  static RestSeedModulePropertiesDefinition from(SeedModulePropertiesDefinition propertiesDefinition) {
    return new RestSeedModulePropertiesDefinition(propertiesDefinition.stream().map(RestSeedModulePropertyDefinition::from).toList());
  }

  @Schema(description = "Definition of the properties for a module")
  public Collection<RestSeedModulePropertyDefinition> getDefinitions() {
    return definitions;
  }
}
