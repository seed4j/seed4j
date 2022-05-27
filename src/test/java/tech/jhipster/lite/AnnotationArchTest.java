package tech.jhipster.lite;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@UnitTest
class AnnotationArchTest {

  @Test
  void shouldHaveUnitTestAnnotation() {
    JavaClasses importedClasses = new ClassFileImporter()
      .importPackages("tech.jhipster.lite")
      .that(are(not(equivalentTo(UnitTest.class)).and(not(equivalentTo(IntegrationTest.class)))));

    ArchRule rule = classes()
      .that()
      .resideInAnyPackage("tech.jhipster.lite..")
      .and()
      .haveSimpleNameEndingWith("Test")
      .should()
      .beAnnotatedWith(UnitTest.class)
      .orShould()
      .beAnnotatedWith(ComponentTest.class)
      .orShould()
      .beAnnotatedWith(Nested.class)
      .orShould()
      .beInterfaces();

    rule.check(importedClasses);
  }

  @Test
  void shouldHaveIntegrationTestAnnotation() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("tech.jhipster.lite");

    ArchRule rule = classes()
      .that()
      .resideInAnyPackage("tech.jhipster.lite..")
      .and()
      .haveSimpleNameEndingWith("IT")
      .should()
      .beAnnotatedWith(IntegrationTest.class)
      .orShould()
      .beAnnotatedWith(Nested.class);

    rule.check(importedClasses);
  }

  @Test
  void shouldHaveGeneratorStepAnnotationInResources() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("tech.jhipster.lite");

    ArchRule rule = methods()
      .that()
      .areDeclaredInClassesThat()
      .resideInAnyPackage("tech.jhipster.lite.generator..")
      .and()
      .areDeclaredInClassesThat()
      .areAnnotatedWith(RestController.class)
      .and()
      .areAnnotatedWith(PostMapping.class)
      .should()
      .beAnnotatedWith(GeneratorStep.class);

    rule.check(importedClasses);
  }
}
