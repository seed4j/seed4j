package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static tech.jhipster.lite.dsl.parser.infrastructure.secondary.DslDomainVisitor.DomainVisitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslContext;
import tech.jhipster.lite.dsl.parser.domain.DslException;
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
        ctx
          .contextBody()
          .primary()
          .forEach(primary -> {
            log.info("primary detect : {}", primary.IDENTIFIER().getText());
          });
      }
      if (ctx.contextBody().primary() != null) {
        ctx
          .contextBody()
          .secondary()
          .forEach(sec -> {
            log.info("secondary detect : {}", sec.IDENTIFIER().getText());
          });
      }

      return contextBuilder.build();
    }
  }
}
