package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.util.Optional;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.*;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.error.domain.Assert;

public class DslEnumVisitor {

  private DslEnumVisitor() {}

  public static class EnumVisitor extends DslBaseVisitor<DslEnum> {

    @Override
    public DslEnum visitEnumType(DslParser.EnumTypeContext ctx) {
      Assert.notNull("ctx", ctx);

      DslEnum.DslEnumBuilder dslEnumBuilder = DslEnum.dslEnumBuilder();
      dslEnumBuilder.name(new EnumName(ctx.IDENTIFIER().getText()));

      BeforeEnumKeyVisitor beforeEnumVisitor = new BeforeEnumKeyVisitor(dslEnumBuilder);

      beforeEnumVisitor.visitBeforeEnum(ctx.beforeEnum());

      EnumKeyVisitor enumKeyVisitor = new EnumKeyVisitor();

      ctx.enumBody().enumKeyValue().forEach(fc -> dslEnumBuilder.addEnumKeyValue(enumKeyVisitor.visitEnumKeyValue(fc)));

      return dslEnumBuilder.build();
    }
  }

  public static class EnumKeyVisitor extends DslBaseVisitor<EnumKeyValue> {

    @Override
    public EnumKeyValue visitEnumKeyValue(DslParser.EnumKeyValueContext ctx) {
      EnumKeyValue.EnumKeyValueBuilder enumKVBuilder = EnumKeyValue.builder();
      enumKVBuilder.enumKey(new EnumKey(ctx.IDENTIFIER_UPPER().getText()));
      if (ctx.beforeEnumKeyValue() != null && ctx.beforeEnumKeyValue().comment() != null) {
        enumKVBuilder.comment(new EnumComment(ctx.beforeEnumKeyValue().comment().getText()));
      }

      if (ctx.STRING() != null) {
        enumKVBuilder.enumValue(new EnumValue(removeFirstAndLastChar(ctx.STRING().getText())));
      }
      return enumKVBuilder.build();
    }

    private String removeFirstAndLastChar(String value) {
      Assert.field("value", value).notBlank();
      return value.substring(1, value.length() - 1);
    }
  }

  public static class BeforeEnumKeyVisitor extends DslBaseVisitor<DslEnum.DslEnumBuilder> {

    private final DslEnum.DslEnumBuilder dslEnumBuilder;

    public BeforeEnumKeyVisitor(DslEnum.DslEnumBuilder dslEnumBuilder) {
      Assert.notNull("dslEnumBuilder", dslEnumBuilder);
      this.dslEnumBuilder = dslEnumBuilder;
    }

    @Override
    public DslEnum.DslEnumBuilder visitBeforeEnum(DslParser.BeforeEnumContext ctx) {
      if (ctx.comment() != null) {
        this.dslEnumBuilder.comment(ctx.comment().getText());
      }
      ctx
        .annotationClass()
        .forEach(annotationContext -> {
          Optional<String> value;
          if (annotationContext.getChildCount() > 2) {
            value = Optional.of(annotationContext.getChild(2).getText());
          } else {
            value = Optional.empty();
          }

          this.dslEnumBuilder.addAnnotation(new DslAnnotation(annotationContext.getChild(0).getText(), value));
        });

      return dslEnumBuilder;
    }
  }
}
