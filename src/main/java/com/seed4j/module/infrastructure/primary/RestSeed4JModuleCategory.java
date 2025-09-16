package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.Seed4JModuleResource;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;
import java.util.Comparator;

@Schema(name = "Seed4JModuleCategory", description = "Information for a module category")
final class RestSeed4JModuleCategory {

  private static final Comparator<RestSeed4JModule> MODULE_COMPARATOR = Comparator.comparing(RestSeed4JModule::getSlug);

  private final String name;
  private final Collection<RestSeed4JModule> modules;

  private RestSeed4JModuleCategory(String name, Collection<RestSeed4JModule> modules) {
    this.name = name;
    this.modules = modules;
  }

  static RestSeed4JModuleCategory from(String category, Collection<Seed4JModuleResource> modules) {
    return new RestSeed4JModuleCategory(category, modules.stream().map(RestSeed4JModule::from).sorted(MODULE_COMPARATOR).toList());
  }

  @Schema(description = "Name of this category", requiredMode = RequiredMode.REQUIRED)
  public String getName() {
    return name;
  }

  @Schema(description = "Modules in this category", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestSeed4JModule> getModules() {
    return modules;
  }
}
