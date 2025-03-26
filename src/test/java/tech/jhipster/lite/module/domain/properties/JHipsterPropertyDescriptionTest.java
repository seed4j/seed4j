package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterPropertyDescriptionTest {

  @Test
  void shouldGetEmptyDescriptionFromNullDescription() {
    assertThat(JHipsterPropertyDescription.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDescriptionFromBlankDescription() {
    assertThat(JHipsterPropertyDescription.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDescriptionFromActualDescription() {
    assertThat(JHipsterPropertyDescription.of("description")).contains(new JHipsterPropertyDescription("description"));
  }
}
