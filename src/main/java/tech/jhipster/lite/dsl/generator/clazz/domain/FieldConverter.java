package tech.jhipster.lite.dsl.generator.clazz.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.generator.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.generator.clazz.domain.assertion.Assertion;
import tech.jhipster.lite.dsl.generator.clazz.domain.field.FieldType;
import tech.jhipster.lite.dsl.generator.clazz.domain.field.FieldTypeImpl;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.Constraint;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class FieldConverter {

  private List<FieldType> fieldTypes = new LinkedList<>();

  private AnnotationConverter annotationConverter;
  private ConfigApp config;

  public FieldConverter(AnnotationConverter annotationConverter, ConfigApp config) {
    this();
    Assert.notNull("annotationConverter", annotationConverter);
    Assert.notNull("config", config);
    this.annotationConverter = annotationConverter;
    this.config = config;
  }

  private FieldConverter() {
    fieldTypes.add(FieldTypeImpl.fieldDouble);
    fieldTypes.add(FieldTypeImpl.fieldBigDecimal);
    fieldTypes.add(FieldTypeImpl.fieldLong);
    fieldTypes.add(FieldTypeImpl.fieldFloat);
    fieldTypes.add(FieldTypeImpl.fieldInteger);

    fieldTypes.add(FieldTypeImpl.fieldString);
    fieldTypes.add(FieldTypeImpl.fieldBlob);

    fieldTypes.add(FieldTypeImpl.fieldDuration);
    fieldTypes.add(FieldTypeImpl.fieldInstant);
    fieldTypes.add(FieldTypeImpl.fieldPeriod);
    fieldTypes.add(FieldTypeImpl.fieldLocalDate);
    fieldTypes.add(FieldTypeImpl.fieldZonedDateTime);
  }

  public void addType(FieldType fieldType) {
    Assert.notNull("fieldType", fieldType);
    fieldTypes.add(fieldType);
  }

  public boolean isKnowType(String typeName) {
    return fieldTypes.stream().anyMatch(s -> s.name().equals(typeName));
  }

  public FieldType getType(String typeName) {
    return fieldTypes.stream().filter(s -> s.name().equals(typeName)).findFirst().orElseThrow();
  }

  public FieldToGenerate convertFieldToGenerate(ClassField classField, ConfigApp config) {
    FieldToGenerate.FieldToGenerateBuilder builderField = FieldToGenerate.fieldToGenerateBuilder();
    builderField.fromClassField(classField);
    if (isKnowType(classField.getType().get())) {
      builderField.type(getType(classField.getType().get()));
    } else {
      System.out.println("Unknown type :" + classField.getType().get());
      // TODO manage type existing class (for import)
      builderField.type(new FieldTypeImpl(classField.getType().get(), Optional.empty()));
    }
    List<Annotation> commonAnnotationAndValidator = new LinkedList<>();

    List<DslAnnotation> knowAnnotation = classField
      .getAnnotation()
      .stream()
      .filter(s -> annotationConverter.isKnowAnnotation(s.name()))
      .toList();
    knowAnnotation.forEach(ano -> commonAnnotationAndValidator.add(annotationConverter.convertAnnotation(ano)));

    List<Constraint> knowConstraint = classField
      .getConstraints()
      .stream()
      .filter(s -> annotationConverter.isKnowAnnotation(s.name()))
      .toList();
    knowConstraint.forEach(val -> commonAnnotationAndValidator.add(annotationConverter.convertFromConstrain(val)));

    if (config.getUseAssertAsValidation().get()) {
      Assertion.AssertionBuilder assertionBuilder = Assertion.builder();
      Assertion assertion = assertionBuilder.from(commonAnnotationAndValidator.stream().distinct().toList(), classField).build();
      builderField.assertion(assertion);
      System.out.println(assertion);
      //si annotation est compatible avec le assert
      //alors add un assert puis supprime annotation
      //builderField.addAssertValidation();
    } else {
      // supprime les doublons éventuels entre les constraints et les annotations
      commonAnnotationAndValidator.stream().distinct().forEach(builderField::addAnnotation);
    }

    return builderField.build();
  }
}
