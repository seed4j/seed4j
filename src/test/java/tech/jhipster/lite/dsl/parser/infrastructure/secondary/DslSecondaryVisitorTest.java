package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslSecondary;
import tech.jhipster.lite.dsl.parser.domain.DslSecondary;

@UnitTest
class DslSecondaryVisitorTest {

  private final DslSecondaryVisitor.SecondaryVisitor domainVisitor = new DslSecondaryVisitor.SecondaryVisitor();

  private DslSecondary getDslSecondary(String fieldDef) {
    DslParser.SecondaryContext ctx = AntlrUtils.getSecondaryContextFromText(fieldDef);
    return domainVisitor.visitSecondary(ctx);
  }

  @Test
  void shouldAcceptSecondaryEmpty() {
    DslSecondary dslSecondary = getDslSecondary("secondary {}");
    assertNotNull(dslSecondary);
    assertTrue(dslSecondary.getDslClasses().isEmpty());
  }

  @Test
  void shouldAcceptDomainWithMultipleClass() {
    DslSecondary dslSecondary = getDslSecondary(
      """
                secondary {
                    class class1 {}
                    class class2 {}
                    enum enum1 {}
                    record record1 {}
                }
                """
    );
    assertNotNull(dslSecondary);
    assertEquals(3, dslSecondary.getDslClasses().size());
    assertEquals(1, dslSecondary.getDslEnum().size());
  }

  @Test
  void shouldAcceptSecondaryWithSimpleFrom() {
    DslSecondary dslSecondary = getDslSecondary(
      """
                      secondary {
                       from MyClass
                      }
                      """
    );
    assertNotNull(dslSecondary);
    assertEquals(1, dslSecondary.getDslFroms().size());
  }

  @Test
  void shouldAcceptSecondaryWithFromMultiple() {
    DslSecondary dslSecondary = getDslSecondary(
      """
                     secondary {
                    from {
                      MyAnotherClass, ddd,dd
                    }
                     }
                     """
    );
    assertNotNull(dslSecondary);
    assertEquals(3, dslSecondary.getDslFroms().size());
  }

  @Test
  void shouldAcceptSecondaryWithMixFrom() {
    DslSecondary dslSecondary = getDslSecondary(
      """
                     secondary {
                    from {MyAnotherClass, ddd,dd}
                      from MyClass
                     }
                     """
    );
    assertNotNull(dslSecondary);
    assertEquals(4, dslSecondary.getDslFroms().size());
  }
}
