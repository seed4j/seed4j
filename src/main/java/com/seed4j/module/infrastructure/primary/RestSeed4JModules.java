package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.Seed4JModulesResources;
import com.seed4j.shared.error.domain.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Schema(name = "Seed4JModules", description = "Available modules")
final class RestSeed4JModules {

  private static final Comparator<RestSeed4JModuleCategory> CATEGORY_COMPARATOR = Comparator.comparing(RestSeed4JModuleCategory::getName);

  private final Collection<RestSeed4JModuleCategory> categories;

  private RestSeed4JModules(Collection<RestSeed4JModuleCategory> categories) {
    this.categories = categories;
  }

  public static RestSeed4JModules from(Seed4JModulesResources modulesResources) {
    Assert.notNull("modulesResources", modulesResources);

    return new RestSeed4JModules(buildCategories(modulesResources));
  }

  private static List<RestSeed4JModuleCategory> buildCategories(Seed4JModulesResources modulesResources) {
    return modulesResources
      .stream()
      .collect(Collectors.groupingBy(module -> module.apiDoc().group().get()))
      .entrySet()
      .stream()
      .map(entry -> RestSeed4JModuleCategory.from(entry.getKey(), entry.getValue()))
      .sorted(CATEGORY_COMPARATOR)
      .toList();
  }

  @Schema(description = "Module categories")
  public Collection<RestSeed4JModuleCategory> getCategories() {
    return categories;
  }
}
