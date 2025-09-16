package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
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
    hiddenResources.setSlugs(null);
    hiddenResources.setTags(null);

    Seed4JModulesResources resources = configuration.seed4JModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(moduleResourcesCollection());
  }

  @Test
  void shouldGetAllResourcesWithoutHiddenAndNestedDependenciesResources() {
    Seed4JHiddenResourcesProperties hiddenResources = new Seed4JHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("module-a"));
    hiddenResources.setTags(null);

    Seed4JModulesResources resources = configuration.seed4JModulesResources(hiddenResources, moduleNestedResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
    logs.shouldHave(Level.INFO, "The following modules are hidden: module-a, module-b, module-c.");
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
