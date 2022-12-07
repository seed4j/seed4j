package tech.jhipster.lite.dsl.generator.clazz.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;

@UnitTest
class ClassConverterTest {

  Pattern UUID_REGEX = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

  @Test
  void shouldBuildClassFromDslWithFolder() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter(config);
    FieldConverter fieldConverter = new FieldConverter(annotationConverter, config);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter, config);
    ClassToGenerate classToGenerate = converter.convertDslClassToGenerate(
      DslClassUtils.createSimpleClass("class2"),
      new DslContextName("mycontext")
    );

    assertNotNull(classToGenerate);
    System.out.println(classToGenerate.getPathFile().toString());
    var test = UUID_REGEX.matcher(classToGenerate.getFolder().toString()).find();
    assertTrue(UUID_REGEX.matcher(classToGenerate.getFolder().toString()).find());
    assertTrue(UUID_REGEX.matcher(classToGenerate.getPathFile().toString()).find());
    assertTrue(classToGenerate.getPathFile().toString().contains("com/mycompany/myapp/mycontext/domain/Class2.java"));
    assertEquals("com.mycompany.myapp.mycontext.domain", classToGenerate.getPackage().get());
  }
}
