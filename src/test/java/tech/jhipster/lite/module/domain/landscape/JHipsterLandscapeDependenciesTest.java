package tech.jhipster.lite.module.domain.landscape;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;

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
