package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterFeatureTest {

  @Test
  void shouldNotBuildWithInvalidFeaturePattern() {
    assertThatThrownBy(() -> new JHipsterFeatureSlug("invalid feature")).isExactlyInstanceOf(InvalidJHipsterSlugException.class);
  }

  @Test
  void shouldGetEmptyFeatureFromNullFeature() {
    assertThat(JHipsterFeatureSlug.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyFeatureFromBlankFeature() {
    assertThat(JHipsterFeatureSlug.of(" ")).isEmpty();
  }

  @Test
  void shouldGetFeatureFromActualFeature() {
    assertThat(JHipsterFeatureSlug.of("this-is-a-feature")).contains(new JHipsterFeatureSlug("this-is-a-feature"));
  }
}
