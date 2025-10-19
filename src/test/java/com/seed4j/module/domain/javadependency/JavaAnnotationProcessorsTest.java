package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.Seed4JModulesFixture.googleAutoServiceAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModulesFixture.hibernateAnnotationProcessor;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaAnnotationProcessorsTest {

  @Test
  void shouldBeEmpty() {
    assertThat(JavaAnnotationProcessors.EMPTY.get(hibernateAnnotationProcessor().id())).isEmpty();
  }

  @Test
  void shouldGetExistingDependency() {
    JavaAnnotationProcessors deps = new JavaAnnotationProcessors(List.of(hibernateAnnotationProcessor()));

    assertThat(deps.get(hibernateAnnotationProcessor().id())).contains(hibernateAnnotationProcessor());
  }

  @Test
  void shouldReturnEmptyForMissingDependency() {
    JavaAnnotationProcessors deps = new JavaAnnotationProcessors(List.of(hibernateAnnotationProcessor()));

    assertThat(deps.get(googleAutoServiceAnnotationProcessor().id())).isEmpty();
  }

  @Test
  void shouldMergeDisjointDependencies() {
    JavaAnnotationProcessors first = new JavaAnnotationProcessors(List.of(hibernateAnnotationProcessor()));
    JavaAnnotationProcessors second = new JavaAnnotationProcessors(List.of(googleAutoServiceAnnotationProcessor()));

    JavaAnnotationProcessors merged = first.merge(second);

    assertThat(merged.get(hibernateAnnotationProcessor().id())).contains(hibernateAnnotationProcessor());
    assertThat(merged.get(googleAutoServiceAnnotationProcessor().id())).contains(googleAutoServiceAnnotationProcessor());
  }

  @Test
  void shouldMergeWithEmptyOther() {
    JavaAnnotationProcessors deps = new JavaAnnotationProcessors(List.of(hibernateAnnotationProcessor()));

    JavaAnnotationProcessors merged = deps.merge(JavaAnnotationProcessors.EMPTY);

    assertThat(merged.get(hibernateAnnotationProcessor().id())).contains(hibernateAnnotationProcessor());
  }
}
