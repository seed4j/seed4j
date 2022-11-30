package tech.jhipster.lite.dsl.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.DslApplication;
import tech.jhipster.lite.dsl.domain.DslContext;
import tech.jhipster.lite.dsl.domain.DslException;

@UnitTest
class DslContextVisitorTest {

  DslContextVisitor.ContextVisitor contextVisitor = new DslContextVisitor.ContextVisitor();

  @Test
  void shouldHaveAValidDslContext() {
    DslParser.ContextContext ctx = AntlrUtils.getContextFromText(
      """
                context ctx1 {
                    domain {}
                }
                """
    );
    DslContext dslContext = contextVisitor.visitContext(ctx);
    assertEquals("Ctx1", dslContext.getName().get());
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
}
