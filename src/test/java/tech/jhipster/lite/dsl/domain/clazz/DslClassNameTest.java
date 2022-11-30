package tech.jhipster.lite.dsl.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class DslClassNameTest {

  @Test
  void shouldReturnValueCapitalize() {
    DslClassName className = new DslClassName("myClass");
    assertEquals("MyClass", className.get());
  }
}
