package tech.jhipster.lite.dsl.parser.domain.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigFluentMethod;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigFluentMethodBuilder;

@UnitTest
class ConfigFluentMethodTest {

  @Test
  void shouldReturnDefaultValue() {
    ConfigFluentMethod config = ConfigFluentMethodBuilder.DEFAULT;
    assertEquals(Boolean.TRUE, config.get());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = { "yes", "true" })
  void shouldBuildTrueValue(String input) {
    ConfigFluentMethod config = ConfigFluentMethodBuilder.builderFluentMethod().fluentMethod(input).build();
    assertTrue(config.get());
  }

  @ParameterizedTest
  @ValueSource(strings = { "no", "false" })
  void shouldBuildFalseValue(String input) {
    ConfigFluentMethod config = ConfigFluentMethodBuilder.builderFluentMethod().fluentMethod(input).build();
    assertFalse(config.get());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = { "  ", "\t", "\n" })
  void shouldBuildDefaultValueIfNull(String input) {
    ConfigFluentMethod config = ConfigFluentMethodBuilder.builderFluentMethod().fluentMethod(input).build();
    assertEquals(ConfigFluentMethod.DEFAULT_VALUE, config.get());
  }
}
