package tech.jhipster.lite.dsl.common.domain.clazz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ClassImportTest {

  @Test
  void shouldDetectWildcardImport() {
    ClassImport classImp = new ClassImport("mon.import.wildcard.*", false);
    assertTrue(classImp.isWildcard());
  }
}
