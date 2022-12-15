package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslContext;
import tech.jhipster.lite.dsl.parser.domain.DslException;

@UnitTest
class DslContextVisitorTest {

  DslContextVisitor.ContextVisitor contextVisitor = new DslContextVisitor.ContextVisitor();

  @Test
  void shouldHaveAValidDslContext() {
    DslParser.ContextContext ctx = AntlrUtils.getContextFromText(
      """
                context Ctx1 {
                    domain {}
                }
                """
    );
    DslContext dslContext = contextVisitor.visitContext(ctx);
    assertEquals("ctx1", dslContext.getName().get());
    assertNotNull(dslContext.getDomain());
  }

  @Test
  void shouldNotBuildContextWithoutDomain() {
    DslParser.ContextContext ctx = AntlrUtils.getContextFromText(
      """
                context ctx1 {                   
                }
                """
    );
    assertThrows(
      DslException.class,
      () -> {
        DslContext dslContext = contextVisitor.visitContext(ctx);
      },
      "DslException was expected"
    );
  }

  @Test
  void shouldDetectPrimaryAndSecondary() {
    DslParser.ContextContext ctx = AntlrUtils.getContextFromText(
      """
                      context ctx1 {
                      domain {}
                      primary primary1  {}
                      secondary secondary1  {}
                      primary primary2  {}
                      }
                      """
    );
    DslContext dslContext = contextVisitor.visitContext(ctx);
  }
}
