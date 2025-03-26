package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterPropertyDefaultValueTest {

  @Test
  void shouldGetEmptyDefaultValueFromNullValue() {
    assertThat(JHipsterPropertyDefaultValue.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyDefaultValueFromBlankValue() {
    assertThat(JHipsterPropertyDefaultValue.of(" ")).isEmpty();
  }

  @Test
  void shouldGetDefaultValueFromActualDefaultValue() {
    assertThat(JHipsterPropertyDefaultValue.of("defaultValue")).contains(new JHipsterPropertyDefaultValue("defaultValue"));
  }
}
