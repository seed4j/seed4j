package tech.jhipster.lite.dsl.parser.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumKey;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumKeyValue;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumName;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumValue;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;

@UnitTest
class DslEnumTest {

  @Test
  void shouldThrowExceptionByDefault() {
    DslEnum.DslEnumBuilder builder = DslEnum.dslEnumBuilder();

    assertThrows(MissingMandatoryValueException.class, builder::build, "MissingMandatoryValueException was expected");
  }

  @Test
  void shouldBuildEnum() {
    DslEnum.DslEnumBuilder builder = DslEnum.dslEnumBuilder();
    DslEnum dslEnum = builder
      .name(new EnumName("myEnum"))
      .comment("my comment")
      .addEnumKeyValue(EnumKeyValue.builder().enumKey(new EnumKey("KEY")).enumValue(new EnumValue("value")).build())
      .build();
    assertEquals("MyEnum", dslEnum.getName().get());
  }

  @Test
  void shouldBuildEnumIfCommentNull() {
    DslEnum.DslEnumBuilder builder = DslEnum.dslEnumBuilder();
    DslEnum dslEnum = builder
      .name(new EnumName("myEnum"))
      .comment(null)
      .addEnumKeyValue(EnumKeyValue.builder().enumKey(new EnumKey("KEY")).enumValue(new EnumValue("value")).build())
      .build();
    assertTrue(dslEnum.getComment().isEmpty());
  }

  @Test
  void shouldBuildEnumIfCommentEmpty() {
    DslEnum.DslEnumBuilder builder = DslEnum.dslEnumBuilder();
    DslEnum dslEnum = builder
      .name(new EnumName("myEnum"))
      .comment("")
      .addEnumKeyValue(EnumKeyValue.builder().enumKey(new EnumKey("KEY")).enumValue(new EnumValue("value")).build())
      .build();
    assertTrue(dslEnum.getComment().isEmpty());
  }
}
