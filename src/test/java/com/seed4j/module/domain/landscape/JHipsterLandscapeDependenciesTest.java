package com.seed4j.module.domain.landscape;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModuleSlug;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterLandscapeDependenciesTest {

  @Test
  void shouldGetEmptyDependenciesFromNullDependencies() {
    assertThat(JHipsterLandscapeDependencies.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDependenciesFromEmptyDependencies() {
    assertThat(JHipsterLandscapeDependencies.of(List.of())).isEmpty();
  }

  @Test
  void shouldGetDependenciesFromActualDependencies() {
    assertThat(JHipsterLandscapeDependencies.of(dependencies())).contains(new JHipsterLandscapeDependencies(dependencies()));
  }

  private List<JHipsterLandscapeDependency> dependencies() {
    return List.of(dependency());
  }

  private JHipsterModuleDependency dependency() {
    return new JHipsterModuleDependency(new JHipsterModuleSlug("slug"));
  }
}
