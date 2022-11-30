package tech.jhipster.lite.dsl.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.StringWithWitespacesException;

@UnitTest
class DslEntityTableNameTest {

  @Test
  void shouldReturnEntityName() {
    DslEntityTableName entityTableName = new DslEntityTableName("My-table");
    assertEquals("My-table", entityTableName.get());
  }

  @Test
  void shouldNotBuild() {
    assertThrows(
      StringWithWitespacesException.class,
      () -> {
        new DslEntityTableName("invalid tableName");
      },
      "StringWithWitespacesException was expected"
    );
  }
}
