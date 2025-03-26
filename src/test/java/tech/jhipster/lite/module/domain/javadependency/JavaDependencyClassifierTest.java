package tech.jhipster.lite.module.domain.javadependency;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JavaDependencyClassifierTest {

  @Test
  void shouldGetEmptyClassifierFromNullClassifier() {
    assertThat(JavaDependencyClassifier.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyClassifierFromBlankClassifier() {
    assertThat(JavaDependencyClassifier.of(" ")).isEmpty();
  }

  @Test
  void shouldGetClassifierFromActualClassifier() {
    assertThat(JavaDependencyClassifier.of("test")).contains(new JavaDependencyClassifier("test"));
  }
}
