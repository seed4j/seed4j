package tech.jhipster.lite.dsl.generator.clazz.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassType;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;

@UnitTest
class ClassToGenerateTest {

  @Test
  void shouldNotBuildByDefault() {
    MissingMandatoryValueException thrown = assertThrows(
      MissingMandatoryValueException.class,
      ClassToGenerate.classToGenerateBuilder()::build,
      "MissingMandatoryValueException was expected"
    );
  }

  @Test
  void shouldBuildClassFromDslClassAndFolder() {
    ClassToGenerate.ClassToGenerateBuilder builder = ClassToGenerate.classToGenerateBuilder();
    ClassToGenerate classToGenerate = builder
      .fromDslClass(DslClassUtils.createSimpleClass("testClass1"))
      .folder(Path.of("/tmp"))
      .file(Path.of("/tmp/testClass1.java"))
      .build();
    assertNotNull(classToGenerate);
    assertEquals("/tmp", classToGenerate.getFolder().toString());
    assertEquals(ClassType.CLASS, classToGenerate.getType());
    assertEquals("TestClass1", classToGenerate.getName().get());
    assertEquals("", classToGenerate.getPackage().get());
    assertEquals(0, classToGenerate.getFields().size()); //create in converter
  }
}
