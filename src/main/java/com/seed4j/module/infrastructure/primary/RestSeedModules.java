package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.SeedModulesResources;
import com.seed4j.shared.error.domain.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Schema(name = "SeedModules", description = "Available modules")
final class RestSeedModules {

  private static final Comparator<RestSeedModuleCategory> CATEGORY_COMPARATOR = Comparator.comparing(RestSeedModuleCategory::getName);

  private final Collection<RestSeedModuleCategory> categories;

  private RestSeedModules(Collection<RestSeedModuleCategory> categories) {
    this.categories = categories;
  }

  public static RestSeedModules from(SeedModulesResources modulesResources) {
    Assert.notNull("modulesResources", modulesResources);

    return new RestSeedModules(buildCategories(modulesResources));
  }

  private static List<RestSeedModuleCategory> buildCategories(SeedModulesResources modulesResources) {
    return modulesResources
      .stream()
      .collect(Collectors.groupingBy(module -> module.apiDoc().group().get()))
      .entrySet()
      .stream()
      .map(entry -> RestSeedModuleCategory.from(entry.getKey(), entry.getValue()))
      .sorted(CATEGORY_COMPARATOR)
      .toList();
  }

  @Schema(description = "Module categories")
  public Collection<RestSeedModuleCategory> getCategories() {
    return categories;
  }
}
