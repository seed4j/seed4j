package tech.jhipster.lite.dsl.infrastructure.secondary;

import static tech.jhipster.lite.dsl.infrastructure.secondary.DslDomainVisitor.DomainVisitor;

import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.DslContext;
import tech.jhipster.lite.dsl.domain.DslException;
import tech.jhipster.lite.error.domain.Assert;

public class DslContextVisitor {

  private DslContextVisitor() {}

  public static class ContextVisitor extends DslBaseVisitor<DslContext> {

    @Override
    public DslContext visitContext(DslParser.ContextContext ctx) {
      Assert.notNull("ctx", ctx);
      DslContext.DslContextBuilder contextBuilder = DslContext.dslContextBuilder();

      DomainVisitor domainVisitor = new DomainVisitor();
      contextBuilder.name(ctx.IDENTIFIER().getText());
      if (ctx.contextBody().domain().size() != 1) {
        throw new DslException("Context can only contains one domain definition");
      }
      contextBuilder.addDomain(domainVisitor.visitDomain(ctx.contextBody().domain().get(0)));

      return contextBuilder.build();
    }
  }
}
