package tech.jhipster.lite.dsl.parser.domain.config;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ConfigUseAssertAsValidationBuilderTest {

  @Test
  void shouldReturnFalseIfNull() {
    ConfigUseAssertAsValidation conf = ConfigUseAssertAsValidationBuilder.builderAssertAsValidation().useAssertAsValidation(null).build();
    assertFalse(conf.useAssert());
  }

  @Test
  void shouldReturnFalseIfEmpty() {
    ConfigUseAssertAsValidation conf = ConfigUseAssertAsValidationBuilder.builderAssertAsValidation().useAssertAsValidation("").build();
    assertFalse(conf.useAssert());
  }

  @Test
  void shouldReturnFalseIfNo() {
    ConfigUseAssertAsValidation conf = ConfigUseAssertAsValidationBuilder.builderAssertAsValidation().useAssertAsValidation("no").build();
    assertFalse(conf.useAssert());
  }

  @Test
  void shouldReturnFalseIfFalse() {
    ConfigUseAssertAsValidation conf = ConfigUseAssertAsValidationBuilder
      .builderAssertAsValidation()
      .useAssertAsValidation("False")
      .build();
    assertFalse(conf.useAssert());
  }

  @Test
  void shouldReturnTrueIfYes() {
    ConfigUseAssertAsValidation conf = ConfigUseAssertAsValidationBuilder.builderAssertAsValidation().useAssertAsValidation("yes").build();
    assertTrue(conf.useAssert());
  }

  @Test
  void shouldReturnTrueIfTrue() {
    ConfigUseAssertAsValidation conf = ConfigUseAssertAsValidationBuilder.builderAssertAsValidation().useAssertAsValidation("true").build();
    assertTrue(conf.useAssert());
  }
}
