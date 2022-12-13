package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.AnnotationConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.ClassConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.FieldConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.TraceProjectFormatter;
import tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.mustache.MustacheRepository;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;

@UnitTest
class DslJavaGeneratorTest {

  @Test
  void shouldRemoveUnknownTypeIfInDsl() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    DslJavaGenerator dslJavaGenerator = new DslJavaGenerator(new MustacheRepository(new TraceProjectFormatter()), converter);

    DslApplication dslApp = DslClassUtils.createApplicationForTestImport();
    ReferenceManager refMgr = new ReferenceManager();
    dslJavaGenerator.prepareGenerate(dslApp, refMgr);

    assertEquals(4, refMgr.getImportsForClass("MyObject1").size());
    assertEquals(3, refMgr.getImportsForClass("MyObject2").size());
    assertEquals(0, refMgr.getUnknownTypeForClass("MyObject2").size());
    assertEquals(0, refMgr.getUnknownTypeForClass("myObject1").size());
  }
}
