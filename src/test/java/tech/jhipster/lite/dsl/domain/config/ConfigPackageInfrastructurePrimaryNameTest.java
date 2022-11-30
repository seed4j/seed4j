package tech.jhipster.lite.dsl.domain.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ConfigPackageInfrastructurePrimaryNameTest {

  @Test
  void shouldAddDefaultPackageIfBlank() {
    ConfigPackageInfrastructurePrimaryName config = new ConfigPackageInfrastructurePrimaryName("");
    assertEquals(ConfigPackageInfrastructurePrimaryName.DEFAULT, config);
  }

  @Test
  void shouldAddDefaultPackageIfNull() {
    ConfigPackageInfrastructurePrimaryName config = new ConfigPackageInfrastructurePrimaryName(null);
    assertEquals(ConfigPackageInfrastructurePrimaryName.DEFAULT, config);
  }

  @Test
  void shouldReturnPackage() {
    ConfigPackageInfrastructurePrimaryName config = new ConfigPackageInfrastructurePrimaryName("test.infra");
    assertEquals("test.infra", config.get());
  }

  @Test
  void shouldReturnFolder() {
    ConfigPackageInfrastructurePrimaryName config = new ConfigPackageInfrastructurePrimaryName("test.infra");
    assertEquals("test/infra", config.path());
  }
}
