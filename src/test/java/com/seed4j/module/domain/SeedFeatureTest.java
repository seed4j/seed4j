package com.seed4j.module.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedFeatureTest {

  @Test
  void shouldNotBuildWithInvalidFeaturePattern() {
    assertThatThrownBy(() -> new SeedFeatureSlug("invalid feature")).isExactlyInstanceOf(InvalidJSeedSlugException.class);
  }

  @Test
  void shouldGetEmptyFeatureFromNullFeature() {
    assertThat(SeedFeatureSlug.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyFeatureFromBlankFeature() {
    assertThat(SeedFeatureSlug.of(" ")).isEmpty();
  }

  @Test
  void shouldGetFeatureFromActualFeature() {
    assertThat(SeedFeatureSlug.of("this-is-a-feature")).contains(new SeedFeatureSlug("this-is-a-feature"));
  }
}
