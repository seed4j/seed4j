package tech.jhipster.lite.dsl.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.DslDomain;

@UnitTest
class DslDomainVisitorTest {

  private final DslDomainVisitor.DomainVisitor domainVisitor = new DslDomainVisitor.DomainVisitor();

  private DslDomain getDslDomain(String fieldDef) {
    DslParser.DomainContext ctx = AntlrUtils.getDomainContextFromText(fieldDef);
    return domainVisitor.visitDomain(ctx);
  }

  @Test
  void shouldAcceptDomainEmpty() {
    DslDomain dslDomain = getDslDomain("domain {}");
    assertNotNull(dslDomain);
    assertTrue(dslDomain.getDslClasses().isEmpty());
  }

  @Test
  void shouldAcceptDomainWithMultipleClass() {
    DslDomain dslDomain = getDslDomain("""
        domain {
            class class1 {}
            class class2 {}
        }
        """);
    assertNotNull(dslDomain);
    assertEquals(2, dslDomain.getDslClasses().size());
  }
}
