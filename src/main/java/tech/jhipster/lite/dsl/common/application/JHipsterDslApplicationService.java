package tech.jhipster.lite.dsl.common.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.dsl.common.domain.DslGenerator;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.DslJavaGenerator;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.GeneratorJavaRepository;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.AnnotationConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.ClassConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.FieldConverter;
import tech.jhipster.lite.dsl.parser.domain.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslRepository;
import tech.jhipster.lite.dsl.parser.domain.JhipsterDslFileToImport;
import tech.jhipster.lite.error.domain.Assert;

@Service
public class JHipsterDslApplicationService {

  private final DslGenerator dslGenerator;

  public JHipsterDslApplicationService(
    DslRepository dslRepository,
    GeneratorJavaRepository generatorJavaRepository,
    AnnotationConverter annotationConverter,
    FieldConverter fieldConverter,
    ClassConverter classConverter
  ) {
    Assert.notNull("dslSaveRepository", dslRepository);
    Assert.notNull("generatorJavaRepository", generatorJavaRepository);
    Assert.notNull("annotationConverter", annotationConverter);
    Assert.notNull("fieldConverter", fieldConverter);
    Assert.notNull("classConverter", classConverter);

    this.dslGenerator =
      new DslGenerator(
        new DslParser(dslRepository),
        new DslJavaGenerator(generatorJavaRepository, annotationConverter, fieldConverter, classConverter)
      );
  }

  public void importAndGenerate(JhipsterDslFileToImport dslToImport) {
    this.dslGenerator.importAndGenerate(dslToImport);
  }
}
