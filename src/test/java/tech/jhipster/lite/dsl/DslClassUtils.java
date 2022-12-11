package tech.jhipster.lite.dsl;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassName;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassType;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.*;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldComment;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldName;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.*;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.AnnotationConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.ClassConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.FieldConverter;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.DslContext;
import tech.jhipster.lite.dsl.parser.domain.DslDomain;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ConstraintWithValue;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.FieldType;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class DslClassUtils {

  private DslClassUtils() {}

  public static ClassToGenerate createClassToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    return converter.convertDslClassToGenerate(createSimpleClass("plane"), new DslContextName("airport"), config);
  }

  public static EnumToGenerate createEnumValueToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    return converter.convertDslEnumToGenerate(createEnumValue("plane"), new DslContextName("airport"), config);
  }

  public static EnumToGenerate createEnumSimpleToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    return converter.convertDslEnumToGenerate(createEnumSimple("plane"), new DslContextName("airport"), config);
  }

  public static ClassToGenerate createClassComplexeToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    return converter.convertDslClassToGenerate(createComplexeClass("plane"), new DslContextName("airport"), config);
  }

  public static ClassToGenerate createRecordToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    return converter.convertDslClassToGenerate(createSimpleRecord("plane"), new DslContextName("airport"), config);
  }

  public static ClassToGenerate createRecordComplexeToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    return converter.convertDslClassToGenerate(createComplexeRecord("plane"), new DslContextName("airport"), config);
  }

  public static DslApplication createSimpleApplication() {
    return DslApplication
      .dslApplilcationBuilder()
      .config(createDefaultConfig())
      .addDslContext(createSimpleContext("context1"))
      .addDslContext(createSimpleContext("context2"))
      .build();
  }

  public static ConfigApp createDefaultConfig() {
    return ConfigApp.configBuilder().build();
  }

  public static ConfigApp createDefaultConfigWithAssert() {
    return ConfigApp.configBuilder().useAssertAsValidation("yes").build();
  }

  public static DslContext createSimpleContext(String name) {
    return DslContext.dslContextBuilder().name(name).addDomain(createSimpleDomain()).build();
  }

  public static DslDomain createSimpleDomain() {
    return DslDomain
      .dslDomainBuilder()
      .addDslClass(createSimpleClass("class1"))
      .addDslClass(createSimpleClass("class2"))
      .addDslClass(createSimpleClass("class3"))
      .build();
  }

  public static DslClass createSimpleRecord(String name) {
    return DslClass
      .dslClassBuilder()
      .name(new ClassName(name))
      .type(ClassType.RECORD)
      .definePackage(ClassPackage.EMPTY)
      .addField(createSimpleField("Integer"))
      .addField(createSimpleField("Long"))
      .addField(createSimpleField("BigDecimal"))
      .addField(createSimpleField("Float"))
      .addField(createSimpleField("Double"))
      .addField(createSimpleField("Instant"))
      .addField(createSimpleField("LocalDate"))
      .addField(createSimpleField("ZonedDateTime"))
      .addField(createSimpleField("Duration"))
      .addField(createSimpleField("Period"))
      .addField(createSimpleField("Blob"))
      //                .addField(createSimpleField("AnyBlob"))
      //                .addField(createSimpleField("ImageBlob"))
      //                .addField(createSimpleField("TextBlob"))
      .addField(createSimpleField("String"))
      .addField(createSimpleField("MyType"))
      .addField(createCompleteField("Integer"))
      .build();
  }

  public static DslClass createSimpleClass(String name) {
    return DslClass
      .dslClassBuilder()
      .name(new ClassName(name))
      .type(ClassType.CLASS)
      .definePackage(ClassPackage.EMPTY)
      .addField(createSimpleField("Integer"))
      .addField(createSimpleField("Long"))
      .addField(createSimpleField("BigDecimal"))
      .addField(createSimpleField("Float"))
      .addField(createSimpleField("Double"))
      .addField(createSimpleField("Instant"))
      .addField(createSimpleField("LocalDate"))
      .addField(createSimpleField("ZonedDateTime"))
      .addField(createSimpleField("Duration"))
      .addField(createSimpleField("Period"))
      .addField(createSimpleField("Blob"))
      //                .addField(createSimpleField("AnyBlob"))
      //                .addField(createSimpleField("ImageBlob"))
      //                .addField(createSimpleField("TextBlob"))
      .addField(createSimpleField("String"))
      .addField(createSimpleField("MyType"))
      .addField(createCompleteField("Integer"))
      .build();
  }

  public static DslEnum createEnumWithAnnotationPackage(String name) {
    return DslEnum
      .dslEnumBuilder()
      .name(new EnumName(name))
      .addAnnotation(new DslAnnotation("package", Optional.of("test")))
      .definePackage(ClassPackage.EMPTY)
      .addEnumKeyValue(createEnumKvSimple("MY_KEY1"))
      .addEnumKeyValue(createEnumKvSimple("MY_KEY2"))
      .addEnumKeyValue(createEnumKvSimple("MY_KEY3"))
      .build();
  }

  public static DslEnum createEnumSimple(String name) {
    return DslEnum
      .dslEnumBuilder()
      .name(new EnumName(name))
      .definePackage(ClassPackage.EMPTY)
      .addEnumKeyValue(createEnumKvSimple("MY_KEY1"))
      .addEnumKeyValue(createEnumKvSimple("MY_KEY2"))
      .addEnumKeyValue(createEnumKvSimple("MY_KEY3"))
      .build();
  }

  public static DslEnum createEnumValue(String name) {
    return DslEnum
      .dslEnumBuilder()
      .name(new EnumName(name))
      .definePackage(ClassPackage.EMPTY)
      .addEnumKeyValue(createEnumKvWithValueString("MY_KEY1"))
      .addEnumKeyValue(createEnumKvWithValueString("MY_KEY2"))
      .addEnumKeyValue(createEnumKvWithValueString("MY_KEY3"))
      .build();
  }

  public static DslClass createComplexeClass(String name) {
    return DslClass
      .dslClassBuilder()
      .name(new ClassName(name))
      .type(ClassType.CLASS)
      .definePackage(ClassPackage.EMPTY)
      .addField(createCompleteField("Integer"))
      .addField(createCompleteField("String"))
      .addField(createCompleteFieldObject("MyObject"))
      .addField(createCompleteFieldObject("Instant"))
      .build();
  }

  public static DslClass createClassWithAnnotationPackage(String name) {
    return DslClass
      .dslClassBuilder()
      .name(new ClassName(name))
      .type(ClassType.CLASS)
      .addAnnotation(new DslAnnotation("package", Optional.of("test")))
      .definePackage(ClassPackage.EMPTY)
      .addField(createCompleteField("Integer"))
      .addField(createCompleteField("String"))
      .addField(createCompleteFieldObject("MyObject"))
      .addField(createCompleteFieldObject("Instant"))
      .build();
  }

  public static DslClass createComplexeRecord(String name) {
    return DslClass
      .dslClassBuilder()
      .name(new ClassName(name))
      .type(ClassType.RECORD)
      .definePackage(ClassPackage.EMPTY)
      .addField(createCompleteField("Integer"))
      .addField(createCompleteField("String"))
      .addField(createCompleteFieldObject("MyObject"))
      .addField(createCompleteFieldObject("Instant"))
      .build();
  }

  public static ClassField createSimpleField(String type) {
    return ClassField.fieldBuilder().type(new FieldType(type)).name(new FieldName("prop" + type)).build();
  }

  public static ClassField createCompleteFieldObject(String type) {
    ClassField.FieldBuilder builder = ClassField
      .fieldBuilder()
      .type(new FieldType(type))
      .name(new FieldName("propComp" + type))
      .comment(new FieldComment("comment for my prop " + type))
      .addAnnotation(new DslAnnotation("NotNull", Optional.empty()));
    if ("Instant".equalsIgnoreCase(type)) {
      builder.addAnnotation(new DslAnnotation("PastOrPresent", Optional.empty()));
    }

    return builder.build();
  }

  public static EnumKeyValue createEnumKvSimple(String name) {
    EnumKeyValue.EnumKeyValueBuilder builder = EnumKeyValue
      .builder()
      .enumKey(new EnumKey("KEY_" + name))
      .comment(new EnumComment("/** comment for key " + name + "*/"));
    return builder.build();
  }

  public static EnumKeyValue createEnumKvWithValueString(String name) {
    EnumKeyValue.EnumKeyValueBuilder builder = EnumKeyValue
      .builder()
      .enumKey(new EnumKey("KEY_" + name))
      .enumValue(new EnumValue("VALUE_" + name))
      .comment(new EnumComment("/** comment for key " + name + "*/"));
    return builder.build();
  }

  public static ClassField createCompleteField(String type) {
    return ClassField
      .fieldBuilder()
      .type(new FieldType(type))
      .name(new FieldName("propComp" + type))
      .comment(new FieldComment("comment for my prop " + type))
      .addAnnotation(new DslAnnotation("NotNull", Optional.empty()))
      .addAnnotation(new DslAnnotation("min", Optional.of("0")))
      .addAnnotation(new DslAnnotation("max", Optional.of("100")))
      .addConstraints(new ConstraintWithValue("min", Optional.of("0")))
      .addConstraints(new ConstraintWithValue("max", Optional.of("100")))
      .build();
  }
}
