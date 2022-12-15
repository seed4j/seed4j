package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslFrom;
import tech.jhipster.lite.dsl.parser.domain.DslSecondary;
import tech.jhipster.lite.error.domain.Assert;

public class DslSecondaryVisitor {

  private DslSecondaryVisitor() {}

  public static class SecondaryVisitor extends DslBaseVisitor<DslSecondary> {

    @Override
    public DslSecondary visitSecondary(DslParser.SecondaryContext ctx) {
      Assert.notNull("ctx", ctx);
      DslSecondary.DslSecondaryBuilder secondaryBuilder = DslSecondary.builder();
      VisitorUtils.getClassAndEnum(ctx.secondaryBody().class_(), ctx.secondaryBody().enumType(), secondaryBuilder);

      ctx
        .secondaryBody()
        .from()
        .forEach(from -> {
          if (from.IDENTIFIER() != null) {
            secondaryBuilder.addDslFrom(new DslFrom(from.IDENTIFIER().getText()));
          }
          if (from.identifier() != null) {
            from.identifier().forEach(id -> secondaryBuilder.addDslFrom(new DslFrom(id.getText())));
          }
        });
      return secondaryBuilder.build();
    }
  }
}
