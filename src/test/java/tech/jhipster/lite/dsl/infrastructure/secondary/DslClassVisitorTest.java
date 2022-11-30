package tech.jhipster.lite.dsl.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.domain.clazz.DslClassName;

@UnitTest
class DslClassVisitorTest {

  private final DslClass.DslClassBuilder dslClassBuilder = DslClass.dslClassBuilder();

  private final DslClassVisitor.BeforeClassVisitor beforeClassVisitor = new DslClassVisitor.BeforeClassVisitor(dslClassBuilder);
  private final DslClassVisitor.ClassVisitor classVisitor = new DslClassVisitor.ClassVisitor();

  private DslClass.DslClassBuilder getBeforeClass(String fieldDef) {
    DslParser.BeforeClassContext ctx = AntlrUtils.getBeforeClassContextFromText(fieldDef);
    return beforeClassVisitor.visitBeforeClass(ctx);
  }

  private DslClass getDslClass(String fieldDef) {
    DslParser.ClassContext ctx = AntlrUtils.getClassContextFromText(fieldDef);
    return classVisitor.visitClass(ctx);
  }

  @Test
  void shouldBeforeClassAcceptSimpleAnnotation() {
    DslClass.DslClassBuilder classBuilder = getBeforeClass("@package class Ship {}");
    DslClass dslClass = classBuilder.name(new DslClassName("test")).build();
    assertEquals(1, dslClass.getAnnotations().size());
    assertEquals("package", dslClass.getAnnotations().get(0).name());
    assertTrue(dslClass.getAnnotations().get(0).value().isEmpty());
  }

  @Test
  void shouldBeforeClassAcceptAnnotationWithSimpleValue() {
    DslClass.DslClassBuilder classBuilder = getBeforeClass("@MyAnnot(testOverride) class Ship {}");
    DslClass dslClass = classBuilder.name(new DslClassName("test")).build();
    assertEquals(1, dslClass.getAnnotations().size());
    assertEquals("MyAnnot", dslClass.getAnnotations().get(0).name());
    assertTrue(dslClass.getAnnotations().get(0).value().isPresent());
    assertEquals("testOverride", dslClass.getAnnotations().get(0).value().get());
  }

  @Test
  void shouldBeforeClassAcceptAnnotationWithComplexValue() {
    DslClass.DslClassBuilder classBuilder = getBeforeClass("@myAnnot(test.override) class Ship {}");
    DslClass dslClass = classBuilder.name(new DslClassName("test")).build();
    assertEquals(1, dslClass.getAnnotations().size());
    assertEquals("myAnnot", dslClass.getAnnotations().get(0).name());
    assertTrue(dslClass.getAnnotations().get(0).value().isPresent());
    assertEquals("test.override", dslClass.getAnnotations().get(0).value().get());
  }

  @Test
  void shouldAddPackageToClassIfAnnotationPackage() {
    DslClass.DslClassBuilder classBuilder = getBeforeClass("@package(test.override) class Ship {}");
    DslClass dslClass = classBuilder.name(new DslClassName("test")).build();
    assertEquals(0, dslClass.getAnnotations().size());
    assertEquals("test.override", dslClass.getPackage().get());
  }

  @Test
  void shouldAddPackageToClassIfAnnotationPackageNoCaseSensitive() {
    DslClass.DslClassBuilder classBuilder = getBeforeClass("@pAckage(test.override) class Ship {}");
    DslClass dslClass = classBuilder.name(new DslClassName("test")).build();
    assertEquals(0, dslClass.getAnnotations().size());
    assertEquals("test.override", dslClass.getPackage().get());
  }

  @Test
  void shouldVisiteClassAcceptFullEntry() {
    DslClass dslClass = getDslClass(
      """
               /** comment for test*/
               @package(test)
               record test {
                  @notBlank
                  shipId UUID
                        
                  @notBlank
                  name String
                        
                  @nullable
                  port Port
                }
                """
    );

    assertNotNull(dslClass);
    assertEquals("record", dslClass.getType().key());
    System.out.println(dslClass);
  }

  @Test
  void shouldVisiteClassAcceptEntryWithoutBeforeClass() {
    DslClass dslClass = getDslClass(
      """
               record test {
                  @notBlank
                  shipId UUID
                        
                  @notBlank
                  name String
                        
                  @nullable
                  port Port
                }
                """
    );

    assertNotNull(dslClass);
    assertEquals("record", dslClass.getType().key());
    System.out.println(dslClass);
  }
}
