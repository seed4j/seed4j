package com.seed4j;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

@UnitTest
class ArtifactBoundariesArchTest {

  private static final JavaClasses classes = new ClassFileImporter()
    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
    .importPackages("com.seed4j");

  @Test
  void modulesShouldOnlyDependOnEngineDomain() {
    noClasses()
      .that()
      .resideInAPackage("com.seed4j.generator..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage(
        "com.seed4j.module.application..",
        "com.seed4j.module.infrastructure..",
        "com.seed4j.history..",
        "com.seed4j.project..",
        "com.seed4j.statistic..",
        "com.seed4j.wire.."
      )
      .because("Modules will be packaged without the engine implementation and the server")
      .check(classes);
  }

  @Test
  void engineShouldNotDependOnModulesNorServer() {
    noClasses()
      .that()
      .resideInAnyPackage("com.seed4j.module..", "com.seed4j.history..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage(
        "com.seed4j.generator..",
        "com.seed4j.shared.slug..",
        "com.seed4j.project..",
        "com.seed4j.statistic..",
        "com.seed4j.wire.."
      )
      .because("The engine will be packaged without the official modules and the server")
      .check(classes);
  }
}
