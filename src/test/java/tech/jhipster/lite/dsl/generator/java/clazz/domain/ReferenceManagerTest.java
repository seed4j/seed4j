package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;

@UnitTest
class ReferenceManagerTest {

  @Test
  void shouldAddImportIfNotExistYet() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("test", new ClassImport("my.domain", false));
    assertEquals(1, refManager.getImportsForClass("test").size());
  }

  @Test
  void shouldAddImportIfMultipleCall() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("test", new ClassImport("my.domain", false));
    refManager.addImportToClass("test", new ClassImport("my.domain2", false));
    assertEquals(2, refManager.getImportsForClass("test").size());
  }

  @Test
  void shouldRemoveDuplicateImport() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("test", new ClassImport("my.domain", false));
    refManager.addImportToClass("test", new ClassImport("my.domain", false));
    assertEquals(1, refManager.getImportsForClass("test").size());
  }

  @Test
  void shouldBeCaseInsensitive() {
    ReferenceManager refManager = new ReferenceManager();
    refManager.addImportToClass("TeSt", new ClassImport("my.domain", false));
    assertEquals(1, refManager.getImportsForClass("tesT").size());
  }
}
