package tech.jhipster.lite.dsl.domain.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ConfigBasePackageTest {

  @Test
  void shouldAddDefaultPackageIfBlank() {
    ConfigBasePackage config = new ConfigBasePackage("");
    assertEquals(ConfigBasePackage.DEFAULT, config);
  }

  @Test
  void shouldAddDefaultPackageIfNull() {
    ConfigBasePackage config = new ConfigBasePackage(null);
    assertEquals(ConfigBasePackage.DEFAULT, config);
  }

  @Test
  void shouldReturnPackage() {
    ConfigBasePackage config = new ConfigBasePackage("test.infra");
    assertEquals("test.infra", config.get());
  }

  @Test
  void shouldReturnFolder() {
    ConfigBasePackage config = new ConfigBasePackage("test.infra");
    assertEquals("test/infra", config.path());
  }
}
