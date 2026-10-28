package com.seed4j.module.domain.javadependency;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

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
