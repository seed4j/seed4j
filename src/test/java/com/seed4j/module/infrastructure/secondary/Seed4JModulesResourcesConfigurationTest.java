package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.defaultModuleResource;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.defaultModuleResourceBuilder;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.moduleNestedFeatureResourcesCollection;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.moduleNestedModuleAndFeatureResourcesCollection;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.moduleNestedResourcesCollection;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.moduleResourcesCollection;
import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class Seed4JModulesResourcesConfigurationTest {

  private static final Seed4JModulesResourcesConfiguration configuration = new Seed4JModulesResourcesConfiguration();

  @Logs
  private LogsSpy logs;

  @Test
  void shouldGetAllResourcesWithoutHiddenResources() {
    Seed4JHiddenResourcesProperties hiddenResources = new Seed4JHiddenResourcesProperties();

    Seed4JModulesResources resources = configuration.seed4JModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(moduleResourcesCollection());
  }

  @Test
  void shouldGetAllResourcesWithoutHiddenAndNestedDependenciesResources() {
    Seed4JHiddenResourcesProperties hiddenResources = new Seed4JHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("module-a"));

    Seed4JModulesResources resources = configuration.seed4JModulesResources(hiddenResources, moduleNestedResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
    logs.shouldHave(Level.INFO, "The following modules are hidden: module-a, module-b, module-c.");
  }

  @Test
  void shouldGetAllResourcesWithoutHiddenAndNestedModuleAndFeatureDependenciesResources() {
    Seed4JHiddenResourcesProperties hiddenResources = new Seed4JHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("module-a"));

    Seed4JModulesResources resources = configuration.seed4JModulesResources(
      hiddenResources,
      moduleNestedModuleAndFeatureResourcesCollection()
    );

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
    logs.shouldHave(Level.INFO, "The following modules are hidden: module-a, module-b, module-c, module-d, module-e, module-f.");
  }

  @Test
  void shouldGetAllResourcesKeepingFeatureDependentOnesWhenOnlySlugIsHidden() {
    Seed4JHiddenResourcesProperties hiddenResources = new Seed4JHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("module-d"));
    List<Seed4JModuleResource> expectedResources = expectedResourcesWhenOnlyModuleDIsHidden();

    Seed4JModulesResources resources = configuration.seed4JModulesResources(hiddenResources, moduleNestedFeatureResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(expectedResources);
    logs.shouldHave(Level.INFO, "The following modules are hidden: module-d.");
  }

  private static List<Seed4JModuleResource> expectedResourcesWhenOnlyModuleDIsHidden() {
    return List.of(
      defaultModuleResource(),
      defaultModuleResourceBuilder().slug("module-a").build(),
      defaultModuleResourceBuilder().slug("module-b").moduleDependency("module-a").build(),
      defaultModuleResourceBuilder().slug("module-c").moduleDependency("module-b").build(),
      defaultModuleResourceBuilder().feature("client-core").slug("module-e").moduleDependency("module-b").build(),
      defaultModuleResourceBuilder().feature("e2e-tests").slug("module-f").featureDependency("client-core").build(),
      defaultModuleResourceBuilder().feature("e2e-tests-detail").slug("module-g").featureDependency("e2e-tests").build()
    );
  }

  @Test
  void shouldHideResourcesBySlugs() {
    Seed4JHiddenResourcesProperties hiddenResources = new Seed4JHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("another-module", "yet-another-module"));

    Seed4JModulesResources resources = configuration.seed4JModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }

  @Test
  void shouldHideResourcesByTags() {
    Seed4JHiddenResourcesProperties hiddenResources = new Seed4JHiddenResourcesProperties();
    hiddenResources.setTags(List.of("tag2", "tag3"));

    Seed4JModulesResources resources = configuration.seed4JModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }
}
