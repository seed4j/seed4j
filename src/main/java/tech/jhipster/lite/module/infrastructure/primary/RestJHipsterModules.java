package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;
import tech.jhipster.lite.shared.error.domain.Assert;

@Schema(name = "JHipsterModules", description = "Available modules")
class RestJHipsterModules {

  private static final Comparator<RestJHipsterModuleCategory> CATEGORY_COMPARATOR = Comparator.comparing(
    RestJHipsterModuleCategory::getName
  );

  private final Collection<RestJHipsterModuleCategory> categories;

  private RestJHipsterModules(Collection<RestJHipsterModuleCategory> categories) {
    this.categories = categories;
  }

  public static RestJHipsterModules from(JHipsterModulesResources modulesResources) {
    Assert.notNull("modulesResources", modulesResources);

    return new RestJHipsterModules(buildCategories(modulesResources));
  }

  private static List<RestJHipsterModuleCategory> buildCategories(JHipsterModulesResources modulesResources) {
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
