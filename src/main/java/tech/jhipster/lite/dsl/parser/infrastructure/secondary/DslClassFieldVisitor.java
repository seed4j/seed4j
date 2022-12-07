package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.util.Optional;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldComment;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldName;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.*;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.FieldType;

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
        .validation()
        .forEach(validationContext -> fieldBuilder.addValidation(new FieldValidationVisitor().visitValidation(validationContext)));

      ctx
        .validatorPattern()
        .forEach(validationContext -> fieldBuilder.addValidation(new FieldValidationVisitor().visitValidatorPattern(validationContext)));
    }

    private static void buildValidator(DslParser.ClassFieldContext ctx, ClassField.FieldBuilder fieldBuilder) {
      ctx
        .minMaxByteValidator()
        .forEach(minmaxContext -> fieldBuilder.addValidator(new FieldValidatorVisitor().visitMinMaxByteValidator(minmaxContext)));

      ctx
        .minMaxNumberValidator()
        .forEach(minmaxContext -> fieldBuilder.addValidator(new FieldValidatorVisitor().visitMinMaxNumberValidator(minmaxContext)));

      ctx
        .minMaxStringValidator()
        .forEach(minmaxContext -> fieldBuilder.addValidator(new FieldValidatorVisitor().visitMinMaxStringValidator(minmaxContext)));
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

  private static class FieldValidatorVisitor extends DslBaseVisitor<FieldValidator> {

    @Override
    public FieldValidator visitMinMaxByteValidator(DslParser.MinMaxByteValidatorContext ctx) {
      String name = ctx.getChild(0).getText();
      String value = ctx.NATURAL_NUMBER().getText();
      return new FieldValidator(name, Integer.parseInt(value));
    }

    @Override
    public FieldValidator visitMinMaxNumberValidator(DslParser.MinMaxNumberValidatorContext ctx) {
      String name = ctx.getChild(0).getText();
      String value = ctx.NATURAL_NUMBER().getText();
      return new FieldValidator(name, Integer.parseInt(value));
    }

    @Override
    public FieldValidator visitMinMaxStringValidator(DslParser.MinMaxStringValidatorContext ctx) {
      String name = ctx.getChild(0).getText();
      String value = ctx.NATURAL_NUMBER().getText();
      return new FieldValidator(name, Integer.parseInt(value));
    }
  }

  private static class FieldValidationVisitor extends DslBaseVisitor<FieldValidation> {

    @Override
    public FieldValidation visitValidation(DslParser.ValidationContext ctx) {
      return new FieldValidation(ctx.getText());
    }
  }
}
