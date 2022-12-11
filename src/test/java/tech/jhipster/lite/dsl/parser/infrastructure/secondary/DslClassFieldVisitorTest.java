package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;

@UnitTest
class DslClassFieldVisitorTest {

  private DslClassFieldVisitor.ClassFieldVisitor fieldVisitor = new DslClassFieldVisitor.ClassFieldVisitor();

  private static Stream<Arguments> provideSimpleStringsForField() {
    return Stream.of(
      Arguments.of("myProperty String", "String"),
      Arguments.of("myProperty Integer", "Integer"),
      Arguments.of("myProperty Integer", "Integer"),
      Arguments.of("myProperty Blob", "Blob"),
      Arguments.of("myProperty AnyBlob", "AnyBlob"),
      Arguments.of("myProperty ImageBlob", "ImageBlob"),
      Arguments.of("myProperty TextBlob", "TextBlob"),
      Arguments.of("myProperty Long", "Long"),
      Arguments.of("myProperty Integer", "Integer"),
      Arguments.of("myProperty BigDecimal", "BigDecimal"),
      Arguments.of("myProperty Float", "Float"),
      Arguments.of("myProperty Double", "Double"),
      Arguments.of("myProperty Instant", "Instant"),
      Arguments.of("myProperty LocalDate", "LocalDate"),
      Arguments.of("myProperty ZonedDateTime", "ZonedDateTime"),
      Arguments.of("myProperty Duration", "Duration"),
      Arguments.of("myProperty Period", "Period"),
      Arguments.of("myProperty UUID", "UUID"),
      Arguments.of("myProperty Enum", "Enum")
    );
  }

  private static Stream<Arguments> provideStringsForFieldWithValidator() {
    return Stream.of(
      Arguments.of("myProperty String minlength(10)", "String", "minlength", "10"),
      Arguments.of("myProperty String maxlength(20)", "String", "maxlength", "20"),
      Arguments.of("myProperty Integer min(1)", "Integer", "min", "1"),
      Arguments.of("myProperty Blob minbytes(8)", "Blob", "minbytes", "8"),
      Arguments.of("myProperty ImageBlob maxbytes(12)", "ImageBlob", "maxbytes", "12")
    );
  }

  private static Stream<Arguments> provideSimpleStringsForValidation() {
    return Stream.of(
      Arguments.of("myProperty String required unique", "required,unique"),
      Arguments.of("myProperty Integer unique required", "unique,required"),
      Arguments.of("myProperty Instant required unique", "required,unique"),
      Arguments.of("myProperty Blob unique required", "required,unique"),
      Arguments.of("myProperty String required", "required"),
      Arguments.of("myProperty Blob unique", "unique")
    );
  }

  private ClassField getClassField(String fieldDef) {
    DslParser.ClassFieldContext fieldCtx = AntlrUtils.getClassFieldContextFromText(fieldDef);
    return fieldVisitor.visitClassField(fieldCtx);
  }

  @ParameterizedTest
  @MethodSource("provideSimpleStringsForField")
  void shouldReturnField(String input, String expected) {
    ClassField field = getClassField(input);
    assertEquals(expected, field.getType().get());
  }

  @Test
  void shouldAcceptNewType() {
    ClassField field = getClassField("myProperty MyNewType");
    assertEquals("MyNewType", field.getType().get());
  }

  @ParameterizedTest
  @MethodSource("provideStringsForFieldWithValidator")
  void shouldReturnFieldWithValidator(String input, String type, String validator, String valueValidator) {
    ClassField field = getClassField(input);
    assertEquals(type, field.getType().get());
    assertEquals(1, field.getConstraints().size());
    assertEquals(validator, field.getConstraints().get(0).name());
    assertEquals(valueValidator, field.getConstraints().get(0).value().get());
  }

  @Test
  void shouldReturnFieldWithMultipleStringValidator() {
    ClassField field = getClassField("myProperty String minlength(10) maxlength(20)");
    assertEquals("String", field.getType().get());
    assertEquals(2, field.getConstraints().size());
    assertEquals("minlength", field.getConstraints().get(0).name());
    assertEquals("maxlength", field.getConstraints().get(1).name());
    assertEquals("10", field.getConstraints().get(0).value().get());
    assertEquals("20", field.getConstraints().get(1).value().get());
  }

  @Test
  void shouldReturnFieldWithMultipleIntegerValidator() {
    ClassField field = getClassField("myProperty Integer min(10) max(20)");
    assertEquals("Integer", field.getType().get());
    assertEquals(2, field.getConstraints().size());
    assertEquals("min", field.getConstraints().get(0).name());
    assertEquals("max", field.getConstraints().get(1).name());
    assertEquals("10", field.getConstraints().get(0).value().get());
    assertEquals("20", field.getConstraints().get(1).value().get());
  }

  @Test
  void shouldReturnFieldWithMultipleByteValidator() {
    ClassField field = getClassField("myProperty Blob minbytes(10) maxbytes(20)");
    assertEquals("Blob", field.getType().get());
    assertEquals(2, field.getConstraints().size());
    assertEquals("minbytes", field.getConstraints().get(0).name());
    assertEquals("maxbytes", field.getConstraints().get(1).name());
    assertEquals("10", field.getConstraints().get(0).value().get());
    assertEquals("20", field.getConstraints().get(1).value().get());
  }

  //
  @Test
  void shouldNoReturnFieldWithInvalidValidator() {
    ClassField field = getClassField("myProperty Integer minlength(10) maxlength(20)");
    assertEquals(0, field.getConstraints().size(), "Integer with (min/max)length");
    field = getClassField("myProperty Blob minlength(10) maxlength(20)");
    assertEquals(0, field.getConstraints().size(), "Blob with (min/max)length");
    field = getClassField("myProperty String min(10) max(20)");
    assertEquals(0, field.getConstraints().size(), "String with min/max");
    field = getClassField("myProperty Blob min(10) max(20)");
    assertEquals(0, field.getConstraints().size(), "Blob with min/max");
    field = getClassField("myProperty Integer minbytes(10) maxbytes(20)");
    assertEquals(0, field.getConstraints().size(), "Integer with (min/max)bytes");
    field = getClassField("myProperty String minbytes(10) maxbytes(20)");
    assertEquals(0, field.getConstraints().size(), "String with (min/max)bytes");
  }

  @Test
  void shouldReturnFieldWithCommentBefore() {
    ClassField field = getClassField(
      """
                /*
                comment for my field
                multiline
                */
                myProperty MyNewProperty
                """
    );
    assertEquals("MyNewProperty", field.getType().get());
    assertTrue(field.getComment().isPresent());
    assertEquals("""
                comment for my field
                multiline""", field.getComment().get().get());
  }

  @Test
  void shouldReturnFieldWithCommentAfter() {
    ClassField field = getClassField(
      """
                      myProperty MyNewProperty /*
                      comment for my field
                      multiline
                      */,
                      """
    );
    assertEquals("MyNewProperty", field.getType().get());
    assertTrue(field.getComment().isPresent());
    assertEquals("""
                comment for my field
                multiline""", field.getComment().get().get());
  }

  @ParameterizedTest
  @MethodSource("provideSimpleStringsForValidation")
  void shouldReturnFieldWithValidation(String input, String type) {
    ClassField field = getClassField(input);
    String[] validation = type.split(",");
    int numberValidation = validation.length;
    assertEquals(numberValidation, field.getConstraints().size());
    field
      .getConstraints()
      .forEach(s -> assertTrue(Arrays.stream(validation).toList().contains(s.name()), s.name() + " not found for " + input));
  }

  @Test
  void shouldReturnFieldWithValidatorPattern() {
    ClassField field = getClassField(
      """
                        email String pattern("/^[^@\s]+@[^@\s]+\\.[^@\s]+$/")
                    """
    );
    assertEquals("String", field.getType().get());
    assertEquals(1, field.getConstraints().size());
    assertEquals("pattern", field.getConstraints().get(0).name());
    assertTrue(field.getConstraints().get(0).value().isPresent());
    assertEquals("/^[^@\s]+@[^@\s]+\\.[^@\s]+$/", field.getConstraints().get(0).value().get());
  }

  @Test
  void shouldReturnFieldWithAnnotation() {
    ClassField field = getClassField(
      """
                      @min(0)
                      @Max(100)
                      @Notnull
                      
                      
                      
                      myProperty MyNewProperty 
                      """
    );
    assertEquals("MyNewProperty", field.getType().get());
    assertEquals(3, field.getAnnotation().size());
    assertTrue(field.getAnnotation().contains(new DslAnnotation("min", Optional.of("0"))));
    assertTrue(field.getAnnotation().contains(new DslAnnotation("max", Optional.of("100"))));
    assertTrue(field.getAnnotation().contains(new DslAnnotation("notnull", Optional.empty())));
  }
}
