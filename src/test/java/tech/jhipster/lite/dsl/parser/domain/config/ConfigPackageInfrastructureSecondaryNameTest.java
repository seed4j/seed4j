package tech.jhipster.lite.dsl.parser.domain.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigPackageInfrastructureSecondaryName;

@UnitTest
class ConfigPackageInfrastructureSecondaryNameTest {

  @Test
  void shouldAddDefaultPackageIfBlank() {
    ConfigPackageInfrastructureSecondaryName config = new ConfigPackageInfrastructureSecondaryName("");
    assertEquals(ConfigPackageInfrastructureSecondaryName.DEFAULT, config);
  }

  @Test
  void shouldAddDefaultPackageIfNull() {
    ConfigPackageInfrastructureSecondaryName config = new ConfigPackageInfrastructureSecondaryName(null);
    assertEquals(ConfigPackageInfrastructureSecondaryName.DEFAULT, config);
  }

  @Test
  void shouldReturnPackage() {
    ConfigPackageInfrastructureSecondaryName config = new ConfigPackageInfrastructureSecondaryName("test.infra");
    assertEquals("test.infra", config.get());
  }

  @Test
  void shouldReturnFolder() {
    ConfigPackageInfrastructureSecondaryName config = new ConfigPackageInfrastructureSecondaryName("test.infra");
    assertEquals("test/infra", config.path());
  }
}
