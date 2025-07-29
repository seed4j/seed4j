package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.resource.JHipsterModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.resource.JHipsterModulesResources;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class JHipsterModulesResourcesConfigurationTest {

  private static final JHipsterModulesResourcesConfiguration configuration = new JHipsterModulesResourcesConfiguration();

  @Logs
  private LogsSpy logs;

  @Test
  void shouldGetAllResourcesWithoutHiddenResources() {
    JHipsterHiddenResourcesProperties hiddenResources = new JHipsterHiddenResourcesProperties();
    hiddenResources.setSlugs(null);
    hiddenResources.setTags(null);

    JHipsterModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(moduleResourcesCollection());
  }

  @Test
  void shouldGetAllResourcesWithoutHiddenAndNestedDependenciesResources() {
    JHipsterHiddenResourcesProperties hiddenResources = new JHipsterHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("module-a"));
    hiddenResources.setTags(null);

    JHipsterModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleNestedResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
    logs.shouldHave(Level.INFO, "The following modules are hidden: module-a, module-b, module-c.");
  }

  @Test
  void shouldHideResourcesBySlugs() {
    JHipsterHiddenResourcesProperties hiddenResources = new JHipsterHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("another-module", "yet-another-module"));

    JHipsterModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }

  @Test
  void shouldHideResourcesByTags() {
    JHipsterHiddenResourcesProperties hiddenResources = new JHipsterHiddenResourcesProperties();
    hiddenResources.setTags(List.of("tag2", "tag3"));

    JHipsterModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }
}
