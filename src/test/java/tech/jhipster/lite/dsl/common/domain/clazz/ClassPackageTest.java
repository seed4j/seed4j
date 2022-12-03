package tech.jhipster.lite.dsl.common.domain.clazz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;

@UnitTest
class ClassPackageTest {

  @Test
  void shouldReturnPackage() {
    ClassPackage classPackage = new ClassPackage("test.infra");
    assertEquals("test.infra", classPackage.get());
  }

  @Test
  void shouldReturnFolder() {
    ClassPackage classPackage = new ClassPackage("test.infra");
    assertEquals("test/infra", classPackage.path());
  }
}
