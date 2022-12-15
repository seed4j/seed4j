package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslDomain;
import tech.jhipster.lite.error.domain.Assert;

public class DslDomainVisitor {

  private DslDomainVisitor() {}

  public static class DomainVisitor extends DslBaseVisitor<DslDomain> {

    @Override
    public DslDomain visitDomain(DslParser.DomainContext ctx) {
      Assert.notNull("ctx", ctx);
      DslDomain.DslDomainBuilder domainBuilder = DslDomain.builder();
      VisitorUtils.getClassAndEnum(ctx.domainBody().class_(), ctx.domainBody().enumType(), domainBuilder);

      return domainBuilder.build();
    }
  }
}
