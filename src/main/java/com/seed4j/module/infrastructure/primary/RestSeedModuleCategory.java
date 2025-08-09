package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.SeedModuleResource;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;
import java.util.Comparator;

@Schema(name = "SeedModuleCategory", description = "Information for a module category")
final class RestSeedModuleCategory {

  private static final Comparator<RestSeedModule> MODULE_COMPARATOR = Comparator.comparing(RestSeedModule::getSlug);

  private final String name;
  private final Collection<RestSeedModule> modules;

  private RestSeedModuleCategory(String name, Collection<RestSeedModule> modules) {
    this.name = name;
    this.modules = modules;
  }

  static RestSeedModuleCategory from(String category, Collection<SeedModuleResource> modules) {
    return new RestSeedModuleCategory(category, modules.stream().map(RestSeedModule::from).sorted(MODULE_COMPARATOR).toList());
  }

  @Schema(description = "Name of this category", requiredMode = RequiredMode.REQUIRED)
  public String getName() {
    return name;
  }

  @Schema(description = "Modules in this category", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestSeedModule> getModules() {
    return modules;
  }
}
