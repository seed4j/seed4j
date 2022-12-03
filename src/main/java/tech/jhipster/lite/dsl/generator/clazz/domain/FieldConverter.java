package tech.jhipster.lite.dsl.generator.clazz.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.generator.clazz.domain.field.FieldType;
import tech.jhipster.lite.dsl.generator.clazz.domain.field.FieldTypeImpl;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.error.domain.Assert;

public class FieldConverter {

  private List<FieldType> fieldTypes = new LinkedList<>();

  public FieldConverter() {
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

  public FieldToGenerate convertFieldToGenerate(ClassField classField) {
    FieldToGenerate.FieldToGenerateBuilder builderField = FieldToGenerate.fieldToGenerateBuilderenerateBuilder();
    builderField.fromClassField(classField);
    if (isKnowType(classField.getType().get())) {
      builderField.type(getType(classField.getType().get()));
    } else {
      System.out.println("Unknown type :" + classField.getType().get());
      // TODO manage type existing class (for import)
      builderField.type(new FieldTypeImpl(classField.getType().get(), Optional.empty()));
    }

    //        builderField.addAnnotation():

    return builderField.build();
  }
}
