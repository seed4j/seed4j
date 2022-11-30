package tech.jhipster.lite.dsl.infrastructure.secondary;

import java.util.Optional;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.DslAnnotation;
import tech.jhipster.lite.dsl.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.domain.clazz.DslClassComment;
import tech.jhipster.lite.dsl.domain.clazz.DslClassName;
import tech.jhipster.lite.dsl.domain.clazz.DslClassType;
import tech.jhipster.lite.error.domain.Assert;

public class DslClassVisitor {

  private DslClassVisitor() {}

  public static class ClassVisitor extends DslBaseVisitor<DslClass> {

    @Override
    public DslClass visitClass(DslParser.ClassContext ctx) {
      Assert.notNull("ctx", ctx);
      DslClass.DslClassBuilder dslClassBuilder = DslClass.dslClassBuilder();
      dslClassBuilder.name(new DslClassName(ctx.IDENTIFIER().getText()));

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
      Assert.notNull("entityBuilder", dslClassBuilder);
      this.dslClassBuilder = dslClassBuilder;
    }

    @Override
    public DslClass.DslClassBuilder visitBeforeClass(DslParser.BeforeClassContext ctx) {
      if (ctx.comment() != null) {
        this.dslClassBuilder.comment(new DslClassComment(ctx.comment().getText()));
      }
      ctx
        .annotation()
        .forEach(annotationContext -> {
          Optional<String> value;
          if (annotationContext.getChildCount() > 2) {
            value = Optional.of(annotationContext.getChild(3).getText());
          } else {
            value = Optional.empty();
          }

          this.dslClassBuilder.addAnnotation(new DslAnnotation(annotationContext.label().getText(), value));
        });

      this.dslClassBuilder.type(DslClassType.from(ctx.classType().getText()));
      return dslClassBuilder;
    }
  }
}
