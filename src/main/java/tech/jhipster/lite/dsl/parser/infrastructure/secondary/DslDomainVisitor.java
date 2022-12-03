package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static tech.jhipster.lite.dsl.parser.infrastructure.secondary.DslClassVisitor.ClassVisitor;

import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslDomain;
import tech.jhipster.lite.error.domain.Assert;

public class DslDomainVisitor {

  private DslDomainVisitor() {}

  public static class DomainVisitor extends DslBaseVisitor<DslDomain> {

    public DslDomain visitDomain(DslParser.DomainContext ctx) {
      Assert.notNull("ctx", ctx);
      DslDomain.DslDomainBuilder domainBuilder = DslDomain.dslDomainBuilder();
      ClassVisitor classVisitor = new ClassVisitor();
      for (DslParser.ClassContext classContext : ctx.domainBody().class_()) {
        domainBuilder.addDslClass(classVisitor.visitClass(classContext));
      }

      return domainBuilder.build();
    }
  }
}
