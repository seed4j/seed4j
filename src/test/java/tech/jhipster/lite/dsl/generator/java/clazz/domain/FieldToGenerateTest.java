package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.lang.model.element.Modifier;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldComment;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldName;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.field.FieldTypeImpl;

@UnitTest
class FieldToGenerateTest {

  @Test
  void shouldBuildGetterForBoolean() {
    FieldToGenerate.FieldToGenerateBuilder builder = FieldToGenerate.builder();
    FieldToGenerate myField = builder
      .type(FieldTypeImpl.fieldBoolean)
      .name(new FieldName("myprop"))
      .addModifiers(Modifier.PUBLIC)
      .comment(new FieldComment("my comment"))
      .build();

    assertEquals("isMyprop", myField.getterName());
    assertEquals("setMyprop", myField.setterName());
  }

  @Test
  void shouldAddDefaultModifierIsNotDefined() {
    FieldToGenerate.FieldToGenerateBuilder builder = FieldToGenerate.builder();
    FieldToGenerate myField = builder.type(FieldTypeImpl.fieldBoolean).name(new FieldName("myprop")).build();

    assertEquals(1, myField.getModifiers().size());
    assertEquals("PRIVATE", myField.getModifiers().get(0).name());
  }
}
