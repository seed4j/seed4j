package tech.jhipster.lite.dsl.generator.java.clazz.domain.converter;

import java.nio.file.Path;
import java.nio.file.Paths;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.EnumToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.FieldToGenerate;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class ClassConverter {

  public static final ClassImport ASSERT_IMPORT = new ClassImport("tech.jhipster.lite.error.domain.Assert", false);

  public static final Path SOURCE_MAIN_JAVA = Path.of("src/main/java");

  private FieldConverter fieldConverter;
  private AnnotationConverter annotationConverter;

  public ClassConverter(FieldConverter fieldConverter, AnnotationConverter annotationConverter) {
    Assert.notNull("fieldConverter", fieldConverter);
    Assert.notNull("annotationConverter", annotationConverter);
    this.fieldConverter = fieldConverter;
    this.annotationConverter = annotationConverter;
  }

  public ClassToGenerate convertDslClassToGenerate(DslClass dslClass, DslContextName contextName, ConfigApp config) {
    Assert.notNull("dslClass", dslClass);
    Assert.notNull("contextName", contextName);

    ClassPackage classPackage = this.annotationConverter.getPackageAnnotation(dslClass.getAnnotations());
    Path packageClass;
    if (classPackage.isEmpty()) {
      packageClass = Paths.get(config.getBasePackage().path(), contextName.get(), config.getPackageDomainName().path());
    } else {
      packageClass = Paths.get(config.getBasePackage().path(), classPackage.path());
    }
    Path folderClass = Paths.get(config.getProjectFolder().get(), SOURCE_MAIN_JAVA.toString(), packageClass.toString());

    Path file = Paths.get(folderClass.toString(), dslClass.getName().get().concat(".java"));

    ClassToGenerate.ClassToGenerateBuilder builder = ClassToGenerate.classToGenerateBuilder();
    builder.fromDslClass(dslClass);
    dslClass
      .getFields()
      .forEach(field -> {
        FieldToGenerate fieldToGenerate = this.fieldConverter.convertFieldToGenerate(field, dslClass, config);
        builder.addField(fieldToGenerate);
        if (fieldToGenerate.getAssertion().isPresent()) {
          builder.addImport(ASSERT_IMPORT);
        }
      });

    //    dslClass
    //      .getAnnotations()
    //      .forEach(annotation -> {
    //        if (!annotationConverter.isAnnotationUseByDsl(annotation)) {
    //          builder.addAnnotation(annotationConverter.convertAnnotation(annotation));
    //        }
    //      });
    return builder.definePackage(new ClassPackage(packageClass.toString())).folder(folderClass).file(file).build();
  }

  public EnumToGenerate convertDslEnumToGenerate(DslEnum dslEnum, DslContextName contextName, ConfigApp config) {
    Assert.notNull("dslEnum", dslEnum);
    Assert.notNull("contextName", contextName);
    ClassPackage classPackage = this.annotationConverter.getPackageAnnotation(dslEnum.getAnnotations());
    Path packageClass;
    if (classPackage.isEmpty()) {
      packageClass = Paths.get(config.getBasePackage().path(), contextName.get(), config.getPackageDomainName().path());
    } else {
      packageClass = Paths.get(config.getBasePackage().path(), classPackage.path());
    }
    Path folderClass = Paths.get(config.getProjectFolder().get(), packageClass.toString());

    Path file = Paths.get(folderClass.toString(), dslEnum.getName().get().concat(".java"));

    EnumToGenerate.EnumToGenerateBuilder builder = EnumToGenerate.enumToGenerateBuilder();
    builder.fromDslEnum(dslEnum);
    dslEnum.getEnumKeyValues().forEach(builder::addEnumKeyValue);

    return builder.definePackage(new ClassPackage(packageClass.toString())).folder(folderClass).file(file).build();
  }
}
