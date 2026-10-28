package com.seed4j.module.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JFeatureTest {

  @Test
  void shouldNotBuildWithInvalidFeaturePattern() {
    assertThatThrownBy(() -> new Seed4JFeatureSlug("invalid feature")).isExactlyInstanceOf(InvalidSeed4JSlugException.class);
  }

  @Test
  void shouldGetEmptyFeatureFromNullFeature() {
    assertThat(Seed4JFeatureSlug.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyFeatureFromBlankFeature() {
    assertThat(Seed4JFeatureSlug.of(" ")).isEmpty();
  }

  @Test
  void shouldGetFeatureFromActualFeature() {
    assertThat(Seed4JFeatureSlug.of("this-is-a-feature")).contains(new Seed4JFeatureSlug("this-is-a-feature"));
  }
}
