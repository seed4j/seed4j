package tech.jhipster.lite.dsl.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class DslContextNameTest {

  @Test
  void shouldReturnValueCapitalize() {
    DslContextName contextName = new DslContextName("myContext");
    assertEquals("MyContext", contextName.get());
  }
}
