package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.util.List;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.ContainsClassBuilder;
import tech.jhipster.lite.error.domain.Assert;

public class VisitorUtils {

  private VisitorUtils() {}

  public static void getClassAndEnum(
    List<DslParser.ClassContext> classCtx,
    List<DslParser.EnumTypeContext> enumCtx,
    ContainsClassBuilder domainBuilder
  ) {
    Assert.field("classCtx", classCtx).notNull().noNullElement();
    Assert.field("enumCtx", enumCtx).notNull().noNullElement();
    DslClassVisitor.ClassVisitor classVisitor = new DslClassVisitor.ClassVisitor();
    for (DslParser.ClassContext classContext : classCtx) {
      domainBuilder.addDslClass(classVisitor.visitClass(classContext));
    }
    DslEnumVisitor.EnumVisitor enumVisitor = new DslEnumVisitor.EnumVisitor();
    for (DslParser.EnumTypeContext enumTypeContext : enumCtx) {
      domainBuilder.addDslEnum(enumVisitor.visitEnumType(enumTypeContext));
    }
  }
}
