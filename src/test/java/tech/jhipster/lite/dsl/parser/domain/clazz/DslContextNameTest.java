package tech.jhipster.lite.dsl.parser.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;

@UnitTest
class DslContextNameTest {

  @Test
  void shouldReturnValueCapitalize() {
    DslContextName contextName = new DslContextName("myContext");
    assertEquals("mycontext", contextName.get());
  }
}
