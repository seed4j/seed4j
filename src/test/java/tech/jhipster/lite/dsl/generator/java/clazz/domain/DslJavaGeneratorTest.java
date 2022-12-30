package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.common.domain.git.GitRepository;
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
    GitRepository mockGit = mock(GitRepository.class);
    doNothing().when(mockGit).init(any());
    DslJavaGenerator dslJavaGenerator = new DslJavaGenerator(new MustacheRepository(new TraceProjectFormatter()), converter, mockGit);

    DslApplication dslApp = DslClassUtils.createApplicationForTestImport();
    ReferenceManager refMgr = new ReferenceManager();
    dslJavaGenerator.prepareGenerate(dslApp, refMgr);

    assertEquals(4, refMgr.getImportsForClass("MyContext", "MyObject1").size());
    assertEquals(2, refMgr.getImportsForClass("MyContext", "MyObject2").size());
    assertEquals(0, refMgr.getUnknownTypeForClass("MyContext", "MyObject2").size());
    assertEquals(0, refMgr.getUnknownTypeForClass("MyContext", "myObject1").size());
  }
}
