package tech.jhipster.lite.dsl.domain.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ConfigPackageInfrastructureNameTest {

  @Test
  void shouldAddDefaultPackageIfBlank() {
    ConfigPackageInfrastructureName config = new ConfigPackageInfrastructureName("");
    assertEquals(ConfigPackageInfrastructureName.DEFAULT, config);
  }

  @Test
  void shouldAddDefaultPackageIfNull() {
    ConfigPackageInfrastructureName config = new ConfigPackageInfrastructureName(null);
    assertEquals(ConfigPackageInfrastructureName.DEFAULT, config);
  }

  @Test
  void shouldReturnPackage() {
    ConfigPackageInfrastructureName config = new ConfigPackageInfrastructureName("test.infra");
    assertEquals("test.infra", config.get());
  }

  @Test
  void shouldReturnFolder() {
    ConfigPackageInfrastructureName config = new ConfigPackageInfrastructureName("test.infra");
    assertEquals("test/infra", config.path());
  }
}
