package tech.jhipster.lite.dsl.parser.domain.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigPackageDomainName;

@UnitTest
class ConfigPackageDomainNameTest {

  @Test
  void shouldAddDefaultPackageIfBlank() {
    ConfigPackageDomainName config = new ConfigPackageDomainName("");
    assertEquals(ConfigPackageDomainName.DEFAULT, config);
  }

  @Test
  void shouldAddDefaultPackageIfNull() {
    ConfigPackageDomainName config = new ConfigPackageDomainName(null);
    assertEquals(ConfigPackageDomainName.DEFAULT, config);
  }

  @Test
  void shouldReturnPackage() {
    ConfigPackageDomainName config = new ConfigPackageDomainName("test.infra");
    assertEquals("test.infra", config.get());
  }

  @Test
  void shouldReturnFolder() {
    ConfigPackageDomainName config = new ConfigPackageDomainName("test.infra");
    assertEquals("test/infra", config.path());
  }
}
