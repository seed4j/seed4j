package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;

@UnitTest
class ReferenceManagerTest {

  @Test
  void shouldAddImportIfNotExistYetWithContext() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("ctx", "test", new ClassImport("my.domain", false));
    assertEquals(1, refManager.getImportsForClass("ctx", "test").size());
  }

  @Test
  void shouldAddImportIfMultipleCallWithContext() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("ctx", "test", new ClassImport("my.domain", false));
    refManager.addImportToClass("ctx", "test", new ClassImport("my.domain2", false));
    assertEquals(2, refManager.getImportsForClass("ctx", "test").size());
  }

  @Test
  void shouldRemoveDuplicateImportWithContext() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("ctx", "test", new ClassImport("my.domain", false));
    refManager.addImportToClass("ctx", "test", new ClassImport("my.domain", false));
    assertEquals(1, refManager.getImportsForClass("ctx", "test").size());
  }

  @Test
  void shouldBeCaseInsensitiveWithContext() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("ctx", "TeSt", new ClassImport("my.domain", false));
    assertEquals(1, refManager.getImportsForClass("ctx", "tesT").size());
  }
}
