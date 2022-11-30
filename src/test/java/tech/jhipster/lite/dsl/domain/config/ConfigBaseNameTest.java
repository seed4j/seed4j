package tech.jhipster.lite.dsl.domain.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

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
