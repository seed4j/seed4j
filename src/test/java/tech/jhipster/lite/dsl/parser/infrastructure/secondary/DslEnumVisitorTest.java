package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;

@UnitTest
class DslEnumVisitorTest {

  private final DslEnumVisitor.EnumVisitor enumVisitor = new DslEnumVisitor.EnumVisitor();

  private DslEnum getDslEnum(String fieldDef) {
    DslParser.EnumTypeContext ctx = AntlrUtils.getEnumTypeContextFromText(fieldDef);
    return enumVisitor.visitEnumType(ctx);
  }

  @Test
  void shouldBuildSimpleEnumIfBreakLineAsSeparator() {
    DslEnum dslEnum = getDslEnum(
      """
           enum Country {
              BELGIUM
               /** from france*/
              FRANCE
              ITALY
            }
            """
    );

    assertEquals(3, dslEnum.getEnumKeyValues().size());
    assertEquals("BELGIUM", dslEnum.getEnumKeyValues().get(0).getKey().get());
    assertEquals("FRANCE", dslEnum.getEnumKeyValues().get(1).getKey().get());
    assertEquals("ITALY", dslEnum.getEnumKeyValues().get(2).getKey().get());
    assertTrue(dslEnum.getEnumKeyValues().get(0).getValue().isEmpty());
    assertTrue(dslEnum.getEnumKeyValues().get(1).getValue().isEmpty());
    assertTrue(dslEnum.getEnumKeyValues().get(2).getValue().isEmpty());
    assertEquals("from france", dslEnum.getEnumKeyValues().get(1).getComment().get().get());
  }

  @Test
  void shouldBuildSimpleEnumIfCommaAsSeparator() {
    DslEnum dslEnum = getDslEnum(
      """
           enum Country {
              BELGIUM,
                 /** from france*/
              FRANCE,
              ITALY,
            }
            """
    );

    assertEquals(3, dslEnum.getEnumKeyValues().size());
    assertEquals("BELGIUM", dslEnum.getEnumKeyValues().get(0).getKey().get());
    assertEquals("FRANCE", dslEnum.getEnumKeyValues().get(1).getKey().get());
    assertEquals("ITALY", dslEnum.getEnumKeyValues().get(2).getKey().get());
    assertTrue(dslEnum.getEnumKeyValues().get(0).getValue().isEmpty());
    assertTrue(dslEnum.getEnumKeyValues().get(1).getValue().isEmpty());
    assertTrue(dslEnum.getEnumKeyValues().get(2).getValue().isEmpty());
    assertEquals("from france", dslEnum.getEnumKeyValues().get(1).getComment().get().get());
  }

  @Test
  void shouldBuildEnumWithValueIfBreakLineAsSeparator() {
    DslEnum dslEnum = getDslEnum(
      """
           enum Country {
              BELGIUM("Belgium")
                 /** from france*/
              FRANCE("France")
              ITALY("Italy")
            }
            """
    );

    assertEquals(3, dslEnum.getEnumKeyValues().size());
    assertEquals("BELGIUM", dslEnum.getEnumKeyValues().get(0).getKey().get());
    assertEquals("FRANCE", dslEnum.getEnumKeyValues().get(1).getKey().get());
    assertEquals("ITALY", dslEnum.getEnumKeyValues().get(2).getKey().get());
    assertEquals("Belgium", dslEnum.getEnumKeyValues().get(0).getValue().get().get());
    assertEquals("France", dslEnum.getEnumKeyValues().get(1).getValue().get().get());
    assertEquals("Italy", dslEnum.getEnumKeyValues().get(2).getValue().get().get());

    assertEquals("from france", dslEnum.getEnumKeyValues().get(1).getComment().get().get());
  }

  @Test
  void shouldBuildEnumWithValueIfCommaAsSeparator() {
    DslEnum dslEnum = getDslEnum(
      """
           enum Country {
              BELGIUM("Belgium"),
              
                 /**
                 * from france
                 */
              FRANCE("France"),
              ITALY("Italy")
            }
            """
    );

    assertEquals(3, dslEnum.getEnumKeyValues().size());
    assertEquals("BELGIUM", dslEnum.getEnumKeyValues().get(0).getKey().get());
    assertEquals("FRANCE", dslEnum.getEnumKeyValues().get(1).getKey().get());
    assertEquals("ITALY", dslEnum.getEnumKeyValues().get(2).getKey().get());
    assertEquals("Belgium", dslEnum.getEnumKeyValues().get(0).getValue().get().get());
    assertEquals("France", dslEnum.getEnumKeyValues().get(1).getValue().get().get());
    assertEquals("Italy", dslEnum.getEnumKeyValues().get(2).getValue().get().get());
    assertEquals("from france", dslEnum.getEnumKeyValues().get(1).getComment().get().get());
  }

  @Test
  void shouldBuildEnumWithCommentBefore() {
    DslEnum dslEnum = getDslEnum(
      """
                 /**
                 * it's my comment
                 */
                 enum Country {
                    BELGIUM("Belgium"),
                       /**
                       * from france
                       */
                    FRANCE("France"),
                    ITALY("Italy")
                  }
                  """
    );

    assertEquals("it's my comment", dslEnum.getComment().get().get());
  }

  @Test
  void shouldBuildEnumWithPackageAnnotation() {
    DslEnum dslEnum = getDslEnum(
      """
@package(tests.mypackage)
                 enum Country {
                    BELGIUM("Belgium"),
                       /**
                       * from france
                       */
                    FRANCE("France"),
                    ITALY("Italy")
                  }
                  """
    );
    assertEquals(1, dslEnum.getAnnotations().size());
    assertEquals("package", dslEnum.getAnnotations().get(0).name());
    assertEquals("tests.mypackage", dslEnum.getAnnotations().get(0).value().get());
  }

  @Test
  void shouldBuildEnumWithIgnore() {
    DslEnum dslEnum = getDslEnum(
      """
@ignore
                 enum Country {
                    BELGIUM("Belgium"),
                       /**
                       * from france
                       */
                    FRANCE("France"),
                    ITALY("Italy")
                  }
                  """
    );
    assertEquals(1, dslEnum.getAnnotations().size());
    assertEquals("ignore", dslEnum.getAnnotations().get(0).name());
  }
}
