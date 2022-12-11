package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.util.Optional;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassComment;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassName;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassType;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.error.domain.Assert;

public class DslClassVisitor {

  private DslClassVisitor() {}

  public static class ClassVisitor extends DslBaseVisitor<DslClass> {

    @Override
    public DslClass visitClass(DslParser.ClassContext ctx) {
      Assert.notNull("ctx", ctx);
      DslClass.DslClassBuilder dslClassBuilder = DslClass.dslClassBuilder();
      dslClassBuilder.name(new ClassName(ctx.IDENTIFIER().getText()));

      BeforeClassVisitor beforeClassVisitor = new BeforeClassVisitor(dslClassBuilder);
      beforeClassVisitor.visitBeforeClass(ctx.beforeClass());

      DslClassFieldVisitor.ClassFieldVisitor fieldVisitor = new DslClassFieldVisitor.ClassFieldVisitor();
      ctx.classBody().classField().forEach(fc -> dslClassBuilder.addField(fieldVisitor.visitClassField(fc)));

      return dslClassBuilder.build();
    }
  }

  public static class BeforeClassVisitor extends DslBaseVisitor<DslClass.DslClassBuilder> {

    private final DslClass.DslClassBuilder dslClassBuilder;

    public BeforeClassVisitor(DslClass.DslClassBuilder dslClassBuilder) {
      Assert.notNull("dslClassBuilder", dslClassBuilder);
      this.dslClassBuilder = dslClassBuilder;
    }

    @Override
    public DslClass.DslClassBuilder visitBeforeClass(DslParser.BeforeClassContext ctx) {
      if (ctx.comment() != null) {
        this.dslClassBuilder.comment(new ClassComment(ctx.comment().getText()));
      }
      ctx
        .annotationClass()
        .forEach(annotationContext -> {
          Optional<String> value = Optional.empty();
          if (annotationContext.getChildCount() > 2) {
            value = Optional.of(annotationContext.getChild(2).getText());
          }

          this.dslClassBuilder.addAnnotation(new DslAnnotation(annotationContext.getChild(0).getText(), value));
        });

      this.dslClassBuilder.type(ClassType.from(ctx.classType().getText()));
      return dslClassBuilder;
    }
  }
}
