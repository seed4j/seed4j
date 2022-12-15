package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslPrimary;

@UnitTest
class DslPrimaryVisitorTest {

  private final DslPrimaryVisitor.PrimaryVisitor domainVisitor = new DslPrimaryVisitor.PrimaryVisitor();

  private DslPrimary getDslPrimary(String fieldDef) {
    DslParser.PrimaryContext ctx = AntlrUtils.getPrimaryContextFromText(fieldDef);
    return domainVisitor.visitPrimary(ctx);
  }

  @Test
  void shouldAcceptPrimaryEmpty() {
    DslPrimary dslPrimary = getDslPrimary("primary {}");
    assertNotNull(dslPrimary);
    assertTrue(dslPrimary.getDslClasses().isEmpty());
  }

  @Test
  void shouldAcceptPrimaryWithMultipleClass() {
    DslPrimary dslPrimary = getDslPrimary(
      """
                primary {
                    class class1 {}
                    class class2 {}
                    enum enum1 {}
                             record record1 {}
                }
                """
    );
    assertNotNull(dslPrimary);
    assertEquals(3, dslPrimary.getDslClasses().size());
    assertEquals(1, dslPrimary.getDslEnum().size());
  }

  @Test
  void shouldAcceptPrimaryWithSimpleFrom() {
    DslPrimary dslPrimary = getDslPrimary(
      """
                      primary {
                       from MyClass
                      }
                      """
    );
    assertNotNull(dslPrimary);
    assertEquals(1, dslPrimary.getDslFroms().size());
  }

  @Test
  void shouldAcceptPrimaryWithFromMultiple() {
    DslPrimary dslPrimary = getDslPrimary(
      """
                     primary {
                    from {
                      MyAnotherClass, ddd,dd
                    }
                     }
                     """
    );
    assertNotNull(dslPrimary);
    assertEquals(3, dslPrimary.getDslFroms().size());
  }

  @Test
  void shouldAcceptPrimaryWithMixFrom() {
    DslPrimary dslPrimary = getDslPrimary(
      """
                     primary {
                    from {MyAnotherClass, ddd,dd}
                      from MyClass
                     }
                     """
    );
    assertNotNull(dslPrimary);
    assertEquals(4, dslPrimary.getDslFroms().size());
  }
}
