package tech.jhipster.lite.dsl.parser.domain.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigBaseName;

@UnitTest
class ConfigBaseNameTest {

  @Test
  void shouldAddDefaultNameIfBlank() {
    ConfigBaseName config = new ConfigBaseName("");
    assertEquals(ConfigBaseName.DEFAULT, config);
  }

  @Test
  void shouldAddDefaultNameIfNull() {
    ConfigBaseName config = new ConfigBaseName(null);
    assertEquals(ConfigBaseName.DEFAULT, config);
  }
}
