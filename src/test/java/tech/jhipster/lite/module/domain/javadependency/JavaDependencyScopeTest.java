package tech.jhipster.lite.module.domain.javadependency;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JavaDependencyScopeTest {

  @Test
  void shouldGetCompileScopeFromNullScope() {
    assertThat(JavaDependencyScope.from(null)).isEqualTo(JavaDependencyScope.COMPILE);
  }

  @ParameterizedTest
  @EnumSource(JavaDependencyScope.class)
  void shouldGetScopeFromInputScope(JavaDependencyScope scope) {
    assertThat(JavaDependencyScope.from(scope)).isEqualTo(scope);
  }

  @Test
  void shouldMergeTestAndCompileScopes() {
    assertThat(JavaDependencyScope.TEST.merge(JavaDependencyScope.COMPILE)).isEqualTo(JavaDependencyScope.COMPILE);
  }

  @Test
  void shouldMergeProvidedAndRuntimeScopes() {
    assertThat(JavaDependencyScope.PROVIDED.merge(JavaDependencyScope.RUNTIME)).isEqualTo(JavaDependencyScope.PROVIDED);
  }
}
