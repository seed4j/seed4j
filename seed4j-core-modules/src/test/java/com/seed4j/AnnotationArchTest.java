package com.seed4j;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

@UnitTest
class AnnotationArchTest {

  private static final JavaClasses tests = new ClassFileImporter()
    .withImportOption(ImportOption.Predefined.ONLY_INCLUDE_TESTS)
    .importPackages("com.seed4j");

  @Test
  void shouldHaveUnitTestAnnotation() {
    classes()
      .that()
      .haveSimpleNameEndingWith("Test")
      .and()
      .areTopLevelClasses()
      .and()
      .areNotAnnotations()
      .should()
      .beMetaAnnotatedWith(UnitTest.class)
      .check(tests);
  }
}
