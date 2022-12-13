package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassComment;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumKey;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumKeyValue;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumName;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumValue;

@UnitTest
class EnumToGenerateTest {

  @Test
  void shouldBuildEnum() {
    EnumToGenerate.EnumToGenerateBuilder builder = EnumToGenerate.enumToGenerateBuilder();
    EnumToGenerate enumToGenerate = builder
      .ignore(true)
      .name(new EnumName("myenum"))
      .definePackage(new ClassPackage("mypackage"))
      .comment(new ClassComment("My comment"))
      .file(Path.of("myfile"))
      .addEnumKeyValue(EnumKeyValue.builder().enumKey(new EnumKey("my_key")).enumValue(new EnumValue("myvalue")).build())
      .folder(Path.of("Myfolder"))
      .build();
    assertNotNull(enumToGenerate);
    assertTrue(enumToGenerate.toString().contains("folder=Myfolder"));
    assertTrue(enumToGenerate.toString().contains("file=myfile"));
  }
}
