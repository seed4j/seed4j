package tech.jhipster.lite.dsl.domain.clazz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.domain.config.ConfigPackageDomainName;

@UnitTest
class DslClassPackageTest {

  @Test
  void shouldReturnPackage() {
    DslClassPackage classPackage = new DslClassPackage("test.infra");
    assertEquals("test.infra", classPackage.get());
  }

  @Test
  void shouldReturnFolder() {
    DslClassPackage classPackage = new DslClassPackage("test.infra");
    assertEquals("test/infra", classPackage.path());
  }
}
