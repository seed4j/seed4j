package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.util.Optional;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldComment;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldName;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.*;
import tech.jhipster.lite.error.domain.Assert;

public class DslClassFieldVisitor {

  private DslClassFieldVisitor() {}

  public static class ClassFieldVisitor extends DslBaseVisitor<ClassField> {

    @Override
    public ClassField visitClassField(DslParser.ClassFieldContext ctx) {
      ClassField.FieldBuilder fieldBuilder = ClassField.fieldBuilder();

      fieldBuilder.name(new FieldName(ctx.IDENTIFIER(0).getText()));
      if (ctx.comment() != null) {
        String comment = ctx.comment().getText();
        fieldBuilder.comment(new FieldComment(comment));
      }
      buildBeforeField(ctx, fieldBuilder);

      buildType(ctx, fieldBuilder);

      buildValidator(ctx, fieldBuilder);

      buildValidation(ctx, fieldBuilder);

      return fieldBuilder.build();
    }

    private static void buildBeforeField(DslParser.ClassFieldContext ctx, ClassField.FieldBuilder fieldBuilder) {
      //TODO manage annotation / modifiers / comment

      if (ctx.beforeClassField().comment() != null && !ctx.beforeClassField().comment().isEmpty()) {
        fieldBuilder.comment(new FieldComment(ctx.beforeClassField().comment().getText()));
      }

      ctx
        .beforeClassField()
        .annotation()
        .forEach(annot -> {
          //todo in annotation visitor?
          if (annot.getChildCount() > 4 && ")".equals(annot.getChild(3).getText())) {
            fieldBuilder.addAnnotation(new DslAnnotation(annot.getChild(0).getText(), Optional.of(annot.getChild(2).getText())));
          }
          if ((annot.getChildCount() - annot.SEPARATOR_JHIPSTER().size()) == 1) {
            fieldBuilder.addAnnotation(new DslAnnotation(annot.getChild(0).getText(), Optional.empty()));
          }
        });
    }

    private static void buildValidation(DslParser.ClassFieldContext ctx, ClassField.FieldBuilder fieldBuilder) {
      ctx
        .constraintCommon()
        .forEach(validationContext -> fieldBuilder.addConstraints(new FieldConstraintVisitor().visitConstraintCommon(validationContext)));

      ctx
        .constraintByte()
        .forEach(validationContext -> fieldBuilder.addConstraints(new FieldConstraintVisitor().visitConstraintByte(validationContext)));

      ctx
        .constraintNumber()
        .forEach(validationContext -> fieldBuilder.addConstraints(new FieldConstraintVisitor().visitConstraintNumber(validationContext)));

      ctx
        .constraintString()
        .forEach(validationContext -> fieldBuilder.addConstraints(new FieldConstraintVisitor().visitConstraintString(validationContext)));
    }

    private static void buildValidator(DslParser.ClassFieldContext ctx, ClassField.FieldBuilder fieldBuilder) {
      //            ctx
      //                    .minMaxByteValidator()
      //                    .forEach(minmaxContext -> fieldBuilder.addConstraints(new FieldValidatorVisitor().visitMinMaxByteValidator(minmaxContext)));
      //
      //            ctx
      //                    .minMaxNumberValidator()
      //                    .forEach(minmaxContext -> fieldBuilder.addConstraints(new FieldValidatorVisitor().visitMinMaxNumberValidator(minmaxContext)));
      //
      //            ctx
      //                    .minMaxStringValidator()
      //                    .forEach(minmaxContext -> fieldBuilder.addConstraints(new FieldValidatorVisitor().visitMinMaxStringValidator(minmaxContext)));
    }

    private static void buildType(DslParser.ClassFieldContext ctx, ClassField.FieldBuilder fieldBuilder) {
      if (ctx.FIELD_TYPE_BLOB() != null) {
        fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_BLOB().getText()));
      } else if (ctx.FIELD_TYPE_NUMBER() != null) {
        fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_NUMBER().getText()));
      } else if (ctx.FIELD_TYPE_TIME() != null) {
        fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_TIME().getText()));
      } else if (ctx.FIELD_TYPE_STRING() != null) {
        fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_STRING().getText()));
      } else if (ctx.FIELD_TYPE_OTHER() != null) {
        fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_OTHER().getText()));
      } else {
        fieldBuilder.type(new FieldType(ctx.IDENTIFIER(1).getText()));
      }
    }
  }

  private static class FieldConstraintVisitor extends DslBaseVisitor<Constraint> {

    @Override
    public Constraint visitConstraintCommon(DslParser.ConstraintCommonContext ctx) {
      String name = ctx.getChild(0).getText();
      return new ConstraintSimple(name);
    }

    @Override
    public Constraint visitConstraintNumber(DslParser.ConstraintNumberContext ctx) {
      return visitChildren(ctx);
    }

    @Override
    public Constraint visitConstraintString(DslParser.ConstraintStringContext ctx) {
      return visitChildren(ctx);
    }

    @Override
    public Constraint visitConstraintByte(DslParser.ConstraintByteContext ctx) {
      return visitChildren(ctx);
    }

    @Override
    public Constraint visitConstraintPattern(DslParser.ConstraintPatternContext ctx) {
      String name = ctx.getChild(0).getText();
      String value = ctx.STRING().getText();

      value = removeFirstAndLastChar(value);
      return new ConstraintWithValue(name, Optional.of(value));
    }

    @Override
    public Constraint visitMinMaxNumberValidator(DslParser.MinMaxNumberValidatorContext ctx) {
      String name = ctx.getChild(0).getText();
      String value = ctx.NATURAL_NUMBER().getText();

      return new ConstraintWithValue(name, Optional.of(value));
    }

    @Override
    public Constraint visitMinMaxStringValidator(DslParser.MinMaxStringValidatorContext ctx) {
      String name = ctx.getChild(0).getText();
      String value = ctx.NATURAL_NUMBER().getText();
      return new ConstraintWithValue(name, Optional.of(value));
    }

    @Override
    public Constraint visitMinMaxByteValidator(DslParser.MinMaxByteValidatorContext ctx) {
      String name = ctx.getChild(0).getText();
      String value = ctx.NATURAL_NUMBER().getText();
      return new ConstraintWithValue(name, Optional.of(value));
    }

    private String removeFirstAndLastChar(String value) {
      Assert.field("value", value).notBlank();
      return value.substring(1, value.length() - 1);
    }
  }
}
