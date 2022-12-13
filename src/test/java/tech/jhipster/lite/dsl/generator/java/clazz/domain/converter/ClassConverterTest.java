package tech.jhipster.lite.dsl.generator.java.clazz.domain.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.EnumToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ReferenceManager;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;

@UnitTest
class ClassConverterTest {

  Pattern UUID_REGEX = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

  @Test
  void shouldBuildClassFromDslWithFolder() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    ReferenceManager referenceManager = new ReferenceManager();
    ClassToGenerate classToGenerate = converter.convertDslClassToGenerate(
      DslClassUtils.createSimpleClass("class2"),
      new DslContextName("mycontext"),
      config,
      referenceManager
    );
    assertNotNull(classToGenerate);
    var test = UUID_REGEX.matcher(classToGenerate.getFolder().toString()).find();
    assertTrue(UUID_REGEX.matcher(classToGenerate.getFolder().toString()).find());
    assertTrue(UUID_REGEX.matcher(classToGenerate.getPathFile().toString()).find());
    assertTrue(classToGenerate.getPathFile().toString().contains("com/mycompany/myapp/mycontext/domain/Class2.java"));
    assertEquals("com.mycompany.myapp.mycontext.domain", classToGenerate.getPackage().get());
  }

  @Test
  void shouldRemoveAnnotationUseByDsl() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    ReferenceManager referenceManager = new ReferenceManager();
    ClassToGenerate classToGenerate = converter.convertDslClassToGenerate(
      DslClassUtils.createClassWithAnnotationPackage("class2"),
      new DslContextName("mycontext"),
      config,
      referenceManager
    );

    assertNotNull(classToGenerate);
    assertEquals(0, classToGenerate.getAnnotations().size());
  }

  @Test
  void shouldAnnotationPackageUseValueFromBasePath() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    ReferenceManager referenceManager = new ReferenceManager();
    ClassToGenerate classToGenerate = converter.convertDslClassToGenerate(
      DslClassUtils.createClassWithAnnotationPackage("class2"),
      new DslContextName("mycontext"),
      config,
      referenceManager
    );

    assertNotNull(classToGenerate);
    assertTrue(classToGenerate.getPathFile().toString().contains("com/mycompany/myapp/test/Class2.java"));
    assertEquals("com.mycompany.myapp.test", classToGenerate.getPackage().get());
  }

  @Test
  void shouldBuildEnumFromDslWithFolder() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    ReferenceManager referenceManager = new ReferenceManager();
    EnumToGenerate enumToGenerate = converter.convertDslEnumToGenerate(
      DslClassUtils.createEnumSimple("class2"),
      new DslContextName("mycontext"),
      config,
      referenceManager
    );
    assertNotNull(enumToGenerate);
    var test = UUID_REGEX.matcher(enumToGenerate.getFolder().toString()).find();
    assertTrue(UUID_REGEX.matcher(enumToGenerate.getFolder().toString()).find());
    assertTrue(UUID_REGEX.matcher(enumToGenerate.getPathFile().toString()).find());
    assertTrue(enumToGenerate.getPathFile().toString().contains("com/mycompany/myapp/mycontext/domain/Class2.java"));
    assertEquals("com.mycompany.myapp.mycontext.domain", enumToGenerate.getPackage().get());
  }

  @Test
  void shouldRemoveAnnotationUseByDslForEnum() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    ReferenceManager referenceManager = new ReferenceManager();
    EnumToGenerate enumToGenerate = converter.convertDslEnumToGenerate(
      DslClassUtils.createEnumWithAnnotationPackage("class2"),
      new DslContextName("mycontext"),
      config,
      referenceManager
    );

    assertNotNull(enumToGenerate);
  }

  @Test
  void shouldIgnoreEnumIfAnnotationIsPresent() {
    ConfigApp config = DslClassUtils.createDefaultConfig();
    AnnotationConverter annotationConverter = new AnnotationConverter();
    FieldConverter fieldConverter = new FieldConverter(annotationConverter);
    ClassConverter converter = new ClassConverter(fieldConverter, annotationConverter);
    ReferenceManager referenceManager = new ReferenceManager();
    EnumToGenerate enumToGenerate = converter.convertDslEnumToGenerate(
      DslClassUtils.createEnumWithAnnotationIgnore("class2"),
      new DslContextName("mycontext"),
      config,
      referenceManager
    );

    assertNotNull(enumToGenerate);
    assertTrue(enumToGenerate.isIgnore());
    //    assertEquals(0, enumToGenerate.getAnnotations().size());
  }
}
