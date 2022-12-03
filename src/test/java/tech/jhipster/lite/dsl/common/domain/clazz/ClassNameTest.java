package tech.jhipster.lite.dsl.common.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassName;

@UnitTest
class ClassNameTest {

  @Test
  void shouldReturnValueCapitalize() {
    ClassName className = new ClassName("myClass");
    assertEquals("MyClass", className.get());
  }
}
