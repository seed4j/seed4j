package tech.jhipster.lite.dsl.generator.java.clazz.domain.converter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.EnumToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.FieldToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ReferenceManager;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class ClassConverter {

  public static final Path SOURCE_MAIN_JAVA = Path.of("src/main/java");

  private FieldConverter fieldConverter;
  private AnnotationConverter annotationConverter;

  public ClassConverter(FieldConverter fieldConverter, AnnotationConverter annotationConverter) {
    Assert.notNull("fieldConverter", fieldConverter);
    Assert.notNull("annotationConverter", annotationConverter);
    this.fieldConverter = fieldConverter;
    this.annotationConverter = annotationConverter;
  }

  public ClassToGenerate convertDslClassToGenerate(
    DslClass dslClass,
    DslContextName contextName,
    ConfigApp config,
    ReferenceManager refManager
  ) {
    Assert.notNull("dslClass", dslClass);
    Assert.notNull("contextName", contextName);
    Assert.notNull("refManager", refManager);

    AtomicReference<Path> packageClass = new AtomicReference<>(
      Paths.get(config.getBasePackage().path(), contextName.get(), config.getPackageDomainName().path())
    );
    AtomicBoolean isIgnore = new AtomicBoolean(false);
    manageAnnotationUseByDsl(contextName, config, dslClass.getAnnotations(), packageClass, isIgnore);

    ClassToGenerate.ClassToGenerateBuilder builder = ClassToGenerate.classToGenerateBuilder();
    if (isIgnore.get()) {
      return builder.ignore(true).build();
    }

    Path folderClass = Paths.get(config.getProjectFolder().get(), SOURCE_MAIN_JAVA.toString(), packageClass.get().toString());

    Path file = Paths.get(folderClass.toString(), dslClass.getName().get().concat(".java"));

    builder.fromDslClass(dslClass).definePackage(new ClassPackage(packageClass.get().toString())).folder(folderClass).file(file);

    dslClass
      .getFields()
      .forEach(field -> {
        FieldToGenerate fieldToGenerate = this.fieldConverter.convertFieldToGenerate(field, dslClass, config, refManager);
        builder.addField(fieldToGenerate);
      });

    refManager.getImportsForClass(dslClass.getName().name()).forEach(builder::addImport);
    return builder.build();
  }

  public EnumToGenerate convertDslEnumToGenerate(
    DslEnum dslEnum,
    DslContextName contextName,
    ConfigApp config,
    ReferenceManager refManager
  ) {
    Assert.notNull("dslEnum", dslEnum);
    Assert.notNull("contextName", contextName);
    Assert.notNull("refManager", refManager);

    AtomicReference<Path> packageClass = new AtomicReference<>(
      Paths.get(config.getBasePackage().path(), contextName.get(), config.getPackageDomainName().path())
    );
    AtomicBoolean isIgnore = new AtomicBoolean(false);
    manageAnnotationUseByDsl(contextName, config, dslEnum.getAnnotations(), packageClass, isIgnore);
    Path folderClass = Paths.get(config.getProjectFolder().get(), SOURCE_MAIN_JAVA.toString(), packageClass.get().toString());

    Path file = Paths.get(folderClass.toString(), dslEnum.getName().get().concat(".java"));

    EnumToGenerate.EnumToGenerateBuilder builder = EnumToGenerate.enumToGenerateBuilder();
    builder.fromDslEnum(dslEnum).definePackage(new ClassPackage(packageClass.get().toString()));

    builder.folder(folderClass).file(file);

    if (isIgnore.get()) {
      return builder.ignore(true).build();
    }

    dslEnum.getEnumKeyValues().forEach(builder::addEnumKeyValue);

    return builder.build();
  }

  private void manageAnnotationUseByDsl(
    DslContextName contextName,
    ConfigApp config,
    List<DslAnnotation> annotationClass,
    AtomicReference<Path> packageClass,
    AtomicBoolean isIgnore
  ) {
    List<DslAnnotation> annotationSpecificDsl = this.annotationConverter.getAnnotationUseByDsl(annotationClass);
    annotationSpecificDsl.forEach(anot -> {
      if (anot.name().equalsIgnoreCase(AnnotationConverter.DSL_ANNOTATION_PACKAGE)) {
        AtomicReference<ClassPackage> classPackage = new AtomicReference<>(ClassPackage.EMPTY);
        anot.value().ifPresent(valuePackage -> classPackage.set(new ClassPackage(valuePackage)));
        if (classPackage.get().isEmpty()) {
          packageClass.set(Paths.get(config.getBasePackage().path(), contextName.get(), config.getPackageDomainName().path()));
        } else {
          packageClass.set(Paths.get(config.getBasePackage().path(), classPackage.get().path()));
        }
      }
      if (anot.name().equalsIgnoreCase(AnnotationConverter.DSL_ANNOTATION_IGNORE)) {
        isIgnore.set(true);
      }
    });
  }
}
