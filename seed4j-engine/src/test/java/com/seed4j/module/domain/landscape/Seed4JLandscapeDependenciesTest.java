package com.seed4j.module.domain.landscape;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModuleSlug;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JLandscapeDependenciesTest {

  @Test
  void shouldGetEmptyDependenciesFromNullDependencies() {
    assertThat(Seed4JLandscapeDependencies.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDependenciesFromEmptyDependencies() {
    assertThat(Seed4JLandscapeDependencies.of(List.of())).isEmpty();
  }

  @Test
  void shouldGetDependenciesFromActualDependencies() {
    assertThat(Seed4JLandscapeDependencies.of(dependencies())).contains(new Seed4JLandscapeDependencies(dependencies()));
  }

  private List<Seed4JLandscapeDependency> dependencies() {
    return List.of(dependency());
  }

  private Seed4JModuleDependency dependency() {
    return new Seed4JModuleDependency(new Seed4JModuleSlug("slug"));
  }
}
