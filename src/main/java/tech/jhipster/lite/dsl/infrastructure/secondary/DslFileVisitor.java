package tech.jhipster.lite.dsl.infrastructure.secondary;

import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.DslApplication;
import tech.jhipster.lite.error.domain.Assert;

public class DslFileVisitor {

  private DslFileVisitor() {}

  public static class FileVisitor extends DslBaseVisitor<DslApplication> {

    @Override
    public DslApplication visitFile_(DslParser.File_Context fileCtx) {
      Assert.notNull("fileCtx", fileCtx);
      DslApplication.DslApplicationBuilder builder = DslApplication.dslApplilcationBuilder();
      builder.config(new DslConfigVisitor.ConfigVisitor().visitConfig(fileCtx.config()));
      DslContextVisitor.ContextVisitor contextVisitor = new DslContextVisitor.ContextVisitor();

      for (DslParser.ContextContext ctx : fileCtx.context()) {
        builder.addDslContext(contextVisitor.visitContext(ctx));
      }

      return builder.build();
    }
  }
}
