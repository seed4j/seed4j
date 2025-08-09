package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.resource.SeedModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.resource.SeedModulesResources;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class SeedModulesResourcesConfigurationTest {

  private static final SeedModulesResourcesConfiguration configuration = new SeedModulesResourcesConfiguration();

  @Logs
  private LogsSpy logs;

  @Test
  void shouldGetAllResourcesWithoutHiddenResources() {
    SeedHiddenResourcesProperties hiddenResources = new SeedHiddenResourcesProperties();
    hiddenResources.setSlugs(null);
    hiddenResources.setTags(null);

    SeedModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(moduleResourcesCollection());
  }

  @Test
  void shouldGetAllResourcesWithoutHiddenAndNestedDependenciesResources() {
    SeedHiddenResourcesProperties hiddenResources = new SeedHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("module-a"));
    hiddenResources.setTags(null);

    SeedModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleNestedResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
    logs.shouldHave(Level.INFO, "The following modules are hidden: module-a, module-b, module-c.");
  }

  @Test
  void shouldHideResourcesBySlugs() {
    SeedHiddenResourcesProperties hiddenResources = new SeedHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("another-module", "yet-another-module"));

    SeedModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }

  @Test
  void shouldHideResourcesByTags() {
    SeedHiddenResourcesProperties hiddenResources = new SeedHiddenResourcesProperties();
    hiddenResources.setTags(List.of("tag2", "tag3"));

    SeedModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }
}
