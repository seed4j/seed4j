package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.SeedModulesResources;
import com.seed4j.shared.error.domain.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Schema(name = "JHipsterModules", description = "Available modules")
final class RestJHipsterModules {

  private static final Comparator<RestJHipsterModuleCategory> CATEGORY_COMPARATOR = Comparator.comparing(
    RestJHipsterModuleCategory::getName
  );

  private final Collection<RestJHipsterModuleCategory> categories;

  private RestJHipsterModules(Collection<RestJHipsterModuleCategory> categories) {
    this.categories = categories;
  }

  public static RestJHipsterModules from(SeedModulesResources modulesResources) {
    Assert.notNull("modulesResources", modulesResources);

    return new RestJHipsterModules(buildCategories(modulesResources));
  }

  private static List<RestJHipsterModuleCategory> buildCategories(SeedModulesResources modulesResources) {
    return modulesResources
      .stream()
      .collect(Collectors.groupingBy(module -> module.apiDoc().group().get()))
      .entrySet()
      .stream()
      .map(entry -> RestJHipsterModuleCategory.from(entry.getKey(), entry.getValue()))
      .sorted(CATEGORY_COMPARATOR)
      .toList();
  }

  @Schema(description = "Module categories")
  public Collection<RestJHipsterModuleCategory> getCategories() {
    return categories;
  }
}
