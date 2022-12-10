package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.AnnotationConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.ClassConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.FieldConverter;
import tech.jhipster.lite.dsl.parser.domain.*;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;

public class DslJavaGenerator {

  private final GeneratorJavaRepository generatorJavaRepository;
  private final AnnotationConverter annotationConverter;
  private final FieldConverter fieldConverter;
  private final ClassConverter classConverter;

  public DslJavaGenerator(
    GeneratorJavaRepository generatorJavaRepository,
    AnnotationConverter annotationConverter,
    FieldConverter fieldConverter,
    ClassConverter classConverter
  ) {
    Assert.notNull("generatorJavaRepository", generatorJavaRepository);
    Assert.notNull("annotationConverter", annotationConverter);
    Assert.notNull("fieldConverter", fieldConverter);
    Assert.notNull("converter", classConverter);

    this.generatorJavaRepository = generatorJavaRepository;
    this.annotationConverter = annotationConverter;
    this.fieldConverter = fieldConverter;
    this.classConverter = classConverter;
  }

  public void generate(DslApplication dslApplication) {
    Assert.notNull("dslApplication", dslApplication);

    ConfigApp configApp = dslApplication.getConfig();

    dslApplication
      .getLstDslContext()
      .forEach(ctx -> {
        ctx
          .getDomain()
          .getDslClasses()
          .forEach(dslClass -> {
            generatorJavaRepository.generate(classConverter.convertDslClassToGenerate(dslClass, ctx.getName(), configApp));
          });
      });

    generatorJavaRepository.format(new ProjectPath(configApp.getProjectFolder().get()));
    // generate
  }
}
