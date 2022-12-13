package tech.jhipster.lite.dsl.generator.java.clazz.domain.converter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.FieldToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ReferenceManager;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.assertion.CodeAssertion;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.field.FieldType;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.field.FieldTypeImpl;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.Constraint;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class FieldConverter {

  public static final ClassImport ASSERT_IMPORT = new ClassImport("tech.jhipster.lite.error.domain.Assert", false);

  private final List<FieldType> fieldTypes = new LinkedList<>();
  private AnnotationConverter annotationConverter;

  public FieldConverter(AnnotationConverter annotationConverter) {
    this();
    Assert.notNull("annotationConverter", annotationConverter);
    this.annotationConverter = annotationConverter;
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
    fieldTypes.add(FieldTypeImpl.fieldBoolean);
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

  public FieldToGenerate convertFieldToGenerate(
    ClassField classField,
    DslClass dslClass,
    ConfigApp config,
    ReferenceManager referenceManager
  ) {
    FieldToGenerate.FieldToGenerateBuilder builderField = FieldToGenerate.builder();
    builderField.fromClassField(classField);
    if (isKnowType(classField.getType().get())) {
      FieldType fieldType = getType(classField.getType().get());
      builderField.type(fieldType);
      fieldType.getImport().ifPresent(val -> referenceManager.addImportToClass(dslClass.getName().name(), val));
    } else {
      referenceManager.addUnknownPropertyTypeInClass(dslClass.getName().get(), classField.getType().get());
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

    if (config.getUseAssertAsValidation().get() || "record".equalsIgnoreCase(dslClass.getType().key())) {
      CodeAssertion.AssertionBuilder assertionBuilder = CodeAssertion.builder();
      CodeAssertion assertion = assertionBuilder.from(commonAnnotationAndValidator.stream().distinct().toList(), classField).build();
      builderField.assertion(assertion);
      commonAnnotationAndValidator.removeIf(item ->
        assertion.getAnnotationManaged().stream().anyMatch(an -> an.equalsIgnoreCase(item.name()))
      );
      referenceManager.addImportToClass(dslClass.getName().name(), ASSERT_IMPORT);
    }

    commonAnnotationAndValidator
      .stream()
      .distinct()
      .forEach(annotation -> {
        builderField.addAnnotation(annotation);
        annotation.getImport().ifPresent(imp -> referenceManager.addImportToClass(dslClass.getName().name(), imp));
      });

    return builderField.build();
  }
}
