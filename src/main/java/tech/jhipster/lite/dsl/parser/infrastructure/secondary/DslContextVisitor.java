package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static tech.jhipster.lite.dsl.parser.infrastructure.secondary.DslDomainVisitor.DomainVisitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslContext;
import tech.jhipster.lite.dsl.parser.domain.DslException;
import tech.jhipster.lite.dsl.parser.domain.DslSecondary;
import tech.jhipster.lite.error.domain.Assert;

public class DslContextVisitor {

  private DslContextVisitor() {}

  public static class ContextVisitor extends DslBaseVisitor<DslContext> {

    private static final Logger log = LoggerFactory.getLogger(ContextVisitor.class);

    @Override
    public DslContext visitContext(DslParser.ContextContext ctx) {
      Assert.notNull("ctx", ctx);
      DslContext.DslContextBuilder contextBuilder = DslContext.dslContextBuilder();

      DomainVisitor domainVisitor = new DomainVisitor();
      contextBuilder.name(ctx.IDENTIFIER().getText());
      if (ctx.contextBody().domain().size() != 1) {
        throw new DslException("Context must contain only one domain definition");
      }
      contextBuilder.addDomain(domainVisitor.visitDomain(ctx.contextBody().domain().get(0)));
      if (ctx.contextBody().primary() != null) {
        DslPrimaryVisitor.PrimaryVisitor primaryVisitor = new DslPrimaryVisitor.PrimaryVisitor();
        ctx
          .contextBody()
          .primary()
          .forEach(primary -> {
            contextBuilder.addPrimary(primaryVisitor.visitPrimary(primary));
          });
      }
      if (ctx.contextBody().primary() != null) {
        DslSecondaryVisitor.SecondaryVisitor secondaryVisitor = new DslSecondaryVisitor.SecondaryVisitor();
        ctx
          .contextBody()
          .secondary()
          .forEach(sec -> {
            contextBuilder.addSecondary(secondaryVisitor.visitSecondary(sec));
          });
      }

      return contextBuilder.build();
    }
  }
}
