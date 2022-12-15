package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static tech.jhipster.lite.dsl.parser.infrastructure.secondary.DslClassVisitor.ClassVisitor;

import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslPrimary;
import tech.jhipster.lite.error.domain.Assert;

public class DslPrimaryVisitor {

  private DslPrimaryVisitor() {}

  public static class PrimaryVisitor extends DslBaseVisitor<DslPrimary> {

    @Override
    public DslPrimary visitPrimary(DslParser.PrimaryContext ctx) {
      Assert.notNull("ctx", ctx);
      DslPrimary.DslPrimaryBuilder primaryBuilder = DslPrimary.builder();
      VisitorUtils.getClassAndEnum(ctx.primaryBody().class_(), ctx.primaryBody().enumType(), primaryBuilder);
      return primaryBuilder.build();
    }
  }
}
