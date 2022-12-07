package tech.jhipster.lite.dsl;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassName;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassType;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldComment;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldName;
import tech.jhipster.lite.dsl.generator.clazz.domain.AnnotationConverter;
import tech.jhipster.lite.dsl.generator.clazz.domain.ClassConverter;
import tech.jhipster.lite.dsl.generator.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.dsl.generator.clazz.domain.FieldConverter;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.DslContext;
import tech.jhipster.lite.dsl.parser.domain.DslDomain;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ConstraintWithValue;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.FieldType;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class DslClassUtils {

  private DslClassUtils() {}

  public static ClassToGenerate createClassToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter(config);
    FieldConverter fieldConverter = new FieldConverter(annotationConverter, config);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter, config);
    return converter.convertDslClassToGenerate(createSimpleClass("plane"), new DslContextName("airport"));
  }

  public static ClassToGenerate createClassComplexeToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter(config);
    FieldConverter fieldConverter = new FieldConverter(annotationConverter, config);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter, config);
    return converter.convertDslClassToGenerate(createComplexeClass("plane"), new DslContextName("airport"));
  }

  public static ClassToGenerate createRecordToGenerate(ConfigApp config) {
    Assert.notNull("config", config);
    AnnotationConverter annotationConverter = new AnnotationConverter(config);
    FieldConverter fieldConverter = new FieldConverter(annotationConverter, config);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter, config);
    return converter.convertDslClassToGenerate(createSimpleRecord("plane"), new DslContextName("airport"));
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

  public static DslClass createComplexeClass(String name) {
    return DslClass
      .dslClassBuilder()
      .name(new ClassName(name))
      .type(ClassType.CLASS)
      .definePackage(ClassPackage.EMPTY)
      .addField(createCompleteField("Integer"))
      .addField(createCompleteField("String"))
      .addField(createCompleteFieldObject("MyObject"))
      .build();
  }

  public static ClassField createSimpleField(String type) {
    return ClassField.fieldBuilder().type(new FieldType(type)).name(new FieldName("prop" + type)).build();
  }

  public static ClassField createCompleteFieldObject(String type) {
    return ClassField
      .fieldBuilder()
      .type(new FieldType(type))
      .name(new FieldName("propComp" + type))
      .comment(new FieldComment("comment for my prop " + type))
      .addAnnotation(new DslAnnotation("NotNull", Optional.empty()))
      .build();
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
