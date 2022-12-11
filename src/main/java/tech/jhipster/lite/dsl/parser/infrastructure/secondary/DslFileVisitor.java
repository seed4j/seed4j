package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.error.domain.Assert;

public class DslFileVisitor {

  private DslFileVisitor() {}

  public static class FileVisitor extends DslBaseVisitor<DslApplication.DslApplicationBuilder> {

    @Override
    public DslApplication.DslApplicationBuilder visitFile_(DslParser.File_Context fileCtx) {
      Assert.notNull("fileCtx", fileCtx);
      DslApplication.DslApplicationBuilder builder = DslApplication.dslApplilcationBuilder();
      builder.config(new DslConfigVisitor.ConfigVisitor().visitConfig(fileCtx.config()));
      DslContextVisitor.ContextVisitor contextVisitor = new DslContextVisitor.ContextVisitor();

      for (DslParser.ContextContext ctx : fileCtx.context()) {
        builder.addDslContext(contextVisitor.visitContext(ctx));
      }

      return builder;
    }
  }
}
