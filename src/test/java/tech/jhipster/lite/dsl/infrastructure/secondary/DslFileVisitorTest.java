package tech.jhipster.lite.dsl.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.DslApplication;
import tech.jhipster.lite.dsl.domain.DslContext;
import tech.jhipster.lite.dsl.domain.DslDomain;
import tech.jhipster.lite.dsl.domain.clazz.DslClass;
import tech.jhipster.lite.error.domain.Assert;

@UnitTest
class DslFileVisitorTest {

  private final DslFileVisitor.FileVisitor classVisitor = new DslFileVisitor.FileVisitor();

  @Test
  void shouldHaveAValidDslApplication() {
    DslParser.File_Context ctx = AntlrUtils.getFileContextFromText(
      """
                config {} 
                context ctx1 {
                    domain {}
                } 
                context ctx2 {
                    domain {}
                } 
                """
    );
    DslApplication dslApp = classVisitor.visitFile_(ctx);
    assertEquals(2, dslApp.getLstDslContext().size());
    assertNotNull(dslApp.getConfig());
  }
}
