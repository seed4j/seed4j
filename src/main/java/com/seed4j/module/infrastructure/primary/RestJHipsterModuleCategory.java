package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.SeedModuleResource;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;
import java.util.Comparator;

@Schema(name = "JHipsterModuleCategory", description = "Information for a module category")
final class RestJHipsterModuleCategory {

  private static final Comparator<RestJHipsterModule> MODULE_COMPARATOR = Comparator.comparing(RestJHipsterModule::getSlug);

  private final String name;
  private final Collection<RestJHipsterModule> modules;

  private RestJHipsterModuleCategory(String name, Collection<RestJHipsterModule> modules) {
    this.name = name;
    this.modules = modules;
  }

  static RestJHipsterModuleCategory from(String category, Collection<SeedModuleResource> modules) {
    return new RestJHipsterModuleCategory(category, modules.stream().map(RestJHipsterModule::from).sorted(MODULE_COMPARATOR).toList());
  }

  @Schema(description = "Name of this category", requiredMode = RequiredMode.REQUIRED)
  public String getName() {
    return name;
  }

  @Schema(description = "Modules in this category", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestJHipsterModule> getModules() {
    return modules;
  }
}
