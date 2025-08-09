package com.seed4j.module.domain.landscape;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModuleSlug;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedLandscapeDependenciesTest {

  @Test
  void shouldGetEmptyDependenciesFromNullDependencies() {
    assertThat(SeedLandscapeDependencies.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDependenciesFromEmptyDependencies() {
    assertThat(SeedLandscapeDependencies.of(List.of())).isEmpty();
  }

  @Test
  void shouldGetDependenciesFromActualDependencies() {
    assertThat(SeedLandscapeDependencies.of(dependencies())).contains(new SeedLandscapeDependencies(dependencies()));
  }

  private List<SeedLandscapeDependency> dependencies() {
    return List.of(dependency());
  }

  private SeedModuleDependency dependency() {
    return new SeedModuleDependency(new SeedModuleSlug("slug"));
  }
}
