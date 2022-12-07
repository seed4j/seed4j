package tech.jhipster.lite.dsl.generator.clazz.domain;

import java.nio.file.Path;
import java.nio.file.Paths;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;

public class ClassConverter {

  private FieldConverter fieldConverter;
  private AnnotationConverter annotationConverter;
  private ConfigApp config;

  public ClassConverter(FieldConverter fieldConverter, AnnotationConverter annotationConverter, ConfigApp configApp) {
    this.fieldConverter = fieldConverter;
    this.annotationConverter = annotationConverter;
    this.config = configApp;
  }

  public ClassToGenerate convertDslClassToGenerate(DslClass dslClass, DslContextName contextName) {
    Path packageClass = Paths.get(config.getBasePackage().path(), contextName.get(), config.getPackageDomainName().path());
    Path folderClass = Paths.get(config.getProjectFolder().get(), packageClass.toString());
    Path file = Paths.get(folderClass.toString(), dslClass.getName().get().concat(".java"));

    ClassToGenerate.ClassToGenerateBuilder builder = ClassToGenerate.classToGenerateBuilder();
    builder.fromDslClass(dslClass);
    dslClass.getFields().forEach(s -> builder.addField(this.fieldConverter.convertFieldToGenerate(s, config)));

    dslClass.getAnnotations().forEach(s -> builder.addAnnotation(annotationConverter.convertAnnotation(s)));
    return builder.definePackage(new ClassPackage(packageClass.toString())).folder(folderClass).file(file).build();
  }
}
