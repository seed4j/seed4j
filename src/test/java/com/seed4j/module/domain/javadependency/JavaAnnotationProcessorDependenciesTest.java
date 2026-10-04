package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.Seed4JModulesFixture.googleAutoServiceAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModulesFixture.hibernateAnnotationProcessor;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaAnnotationProcessorDependenciesTest {

  @Test
  void shouldBeEmpty() {
    assertThat(JavaAnnotationProcessorDependencies.EMPTY.get(hibernateAnnotationProcessor().id())).isEmpty();
  }

  @Test
  void shouldGetExistingDependency() {
    JavaAnnotationProcessorDependencies deps = new JavaAnnotationProcessorDependencies(List.of(hibernateAnnotationProcessor()));

    assertThat(deps.get(hibernateAnnotationProcessor().id())).contains(hibernateAnnotationProcessor());
  }

  @Test
  void shouldReturnEmptyForMissingDependency() {
    JavaAnnotationProcessorDependencies deps = new JavaAnnotationProcessorDependencies(List.of(hibernateAnnotationProcessor()));

    assertThat(deps.get(googleAutoServiceAnnotationProcessor().id())).isEmpty();
  }

  @Test
  void shouldMergeDisjointDependencies() {
    JavaAnnotationProcessorDependencies first = new JavaAnnotationProcessorDependencies(List.of(hibernateAnnotationProcessor()));
    JavaAnnotationProcessorDependencies second = new JavaAnnotationProcessorDependencies(List.of(googleAutoServiceAnnotationProcessor()));

    JavaAnnotationProcessorDependencies merged = first.merge(second);

    assertThat(merged.get(hibernateAnnotationProcessor().id())).contains(hibernateAnnotationProcessor());
    assertThat(merged.get(googleAutoServiceAnnotationProcessor().id())).contains(googleAutoServiceAnnotationProcessor());
  }

  @Test
  void shouldMergeWithEmptyOther() {
    JavaAnnotationProcessorDependencies deps = new JavaAnnotationProcessorDependencies(List.of(hibernateAnnotationProcessor()));

    JavaAnnotationProcessorDependencies merged = deps.merge(JavaAnnotationProcessorDependencies.EMPTY);

    assertThat(merged.get(hibernateAnnotationProcessor().id())).contains(hibernateAnnotationProcessor());
  }
}
