package tech.jhipster.lite;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@UnitTest
class HexagonalArchTest {

  private static final String ROOT_PACKAGE = "tech.jhipster.lite";

  private static final JavaClasses classes = new ClassFileImporter()
    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
    .importPackages(ROOT_PACKAGE);

  private static final Collection<String> businessContexts = buildBusinessContexts();

  private static final Collection<String> sharedKernels = buildSharedKernels();

  // the empty package is related to: https://github.com/TNG/ArchUnit/issues/191#issuecomment-507964792
  private static final Collection<String> commonPackages = Arrays.asList("java..", "org.slf4j..", "org.apache.commons..", "");

  private static final Collection<String> sharedKernelsPackages = buildSharedKernelsPackages();

  private static Collection<String> buildBusinessContexts() {
    return getPackageAnnotatedBy(BusinessContext.class);
  }

  private static Collection<String> buildSharedKernels() {
    return getPackageAnnotatedBy(SharedKernel.class);
  }

  private static Collection<String> buildSharedKernelsPackages() {
    return getPackageAnnotatedBy(SharedKernel.class).stream().map(path -> path + "..").toList();
  }

  private static Collection<String> getPackageAnnotatedBy(Class<? extends Annotation> annotationClass) throws AssertionError {
    try {
      return Files
        .walk(Paths.get("src", "main", "java", "tech", "jhipster", "lite"))
        .filter(path -> path.toString().endsWith("package-info.java"))
        .map(toPackageName())
        .map(path -> path.replaceAll("[\\/]", "."))
        .map(path -> path.replaceAll("[\\\\]", "."))
        .map(path -> path.replace("src.main.java.", ""))
        .map(toPackage())
        .filter(pack -> pack.getAnnotation(annotationClass) != null)
        .map(Package::getName)
        .toList();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private static Function<Path, String> toPackageName() {
    return path -> {
      String stringPath = path.toString();
      return stringPath.substring(0, stringPath.lastIndexOf("."));
    };
  }

  private static Function<String, Package> toPackage() {
    return path -> {
      try {
        return Class.forName(path).getPackage();
      } catch (ClassNotFoundException e) {
        throw new AssertionError();
      }
    };
  }

  @Test
  void shouldNotHaveInfrastructureDependenciesInApplication() {
    noClasses()
      .that()
      .resideInAnyPackage("..application..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage("..infrastructure..")
      .because("Application should only depend on domain, not on infrastructure")
      .check(classes);
  }

  @Test
  void shouldNotDependOnOtherContexts() {
    businessContexts.forEach(context -> {
      String[] otherContexts = otherContexts(context);

      noClasses()
        .that()
        .resideInAnyPackage(context + "..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(otherContexts)
        .because("Contexts can only depend on classes in the same context or in common")
        .check(classes);
    });
  }

  private String[] otherContexts(String context) {
    return businessContexts.stream().filter(other -> !context.equals(other)).map(name -> name + ".domain..").toArray(String[]::new);
  }

  @Test
  void shouldNotHaveFrameworkDependenciesInContext() {
    businessContexts.forEach(context ->
      classes()
        .that()
        .resideInAnyPackage(context + ".domain..")
        .should()
        .onlyDependOnClassesThat()
        .resideInAnyPackage(authorizedContextPackages(context + ".domain.."))
        .because("Domain model should only depend on himself and a very limited set of external dependencies")
        .check(classes)
    );
  }

  @Test
  void shouldNotHaveFrameworkDependenciesInSharedKernel() {
    sharedKernels.forEach(context ->
      classes()
        .that()
        .resideInAnyPackage(context + ".domain..")
        .and()
        .resideOutsideOfPackage("tech.jhipster.lite.common.domain")
        .should()
        .onlyDependOnClassesThat()
        .resideInAnyPackage(authorizedContextPackages(context + ".domain.."))
        .because("Domain model should only depend on himself and a very limited set of external dependencies")
        .check(classes)
    );
  }

  private String[] authorizedContextPackages(String packageName) {
    return Stream.of(List.of(packageName), commonPackages, sharedKernelsPackages).flatMap(Collection::stream).toArray(String[]::new);
  }

  @Test
  void shouldHaveNoDependencyToApplicationFromSecondary() {
    noClasses()
      .that()
      .resideInAnyPackage("..infrastructure.secondary..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage("..application..")
      .because("Secondary should not depend on application")
      .check(classes);
  }

  @Test
  void shouldNotHaveInfrastructureDependenciesFromDomain() {
    noClasses()
      .that()
      .resideInAnyPackage("..domain..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage("..infrastructure..")
      .check(classes);
  }

  @Test
  void shouldNotHaveContextDependenciesForSharedKernels() {
    sharedKernelsPackages.forEach(kernel ->
      noClasses()
        .that()
        .resideInAnyPackage(kernel)
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(businessContexts.stream().map(context -> context + ".domain..").toArray(String[]::new))
        .orShould()
        .resideInAnyPackage(businessContexts.stream().map(context -> context + ".application..").toArray(String[]::new))
        .orShould()
        .resideInAnyPackage(businessContexts.stream().map(context -> context + ".infrastructure.secondary..").toArray(String[]::new))
        .because("Shared kernels should not have dependencies to bounded contexts")
        .check(classes)
    );
  }

  @Test
  void shouldNotHavePublicControllers() {
    noClasses().that().areAnnotatedWith(RestController.class).or().areAnnotatedWith(Controller.class).should().bePublic().check(classes);
  }

  @Test
  void domainShouldNotInteractWithOutside() {
    JavaClasses importedClasses = new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("tech.jhipster.lite");

    noClasses()
      .that()
      .resideInAnyPackage("..domain..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage("..application..", "..infrastructure..", "..primary..", "..secondary..")
      .because("Domain should not interact with application, infrastructure, primary or secondary")
      .check(importedClasses);
  }

  @Test
  void applicationShouldNotInteractWithSecondary() {
    JavaClasses importedClasses = new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("tech.jhipster.lite");

    noClasses()
      .that()
      .resideInAnyPackage("..application..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage("..secondary..")
      .because("Application should not interact with secondary")
      .check(importedClasses);
  }

  @Test
  void primaryShouldNotInteractWithSecondary() {
    JavaClasses importedClasses = new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("tech.jhipster.lite");

    noClasses()
      .that()
      .resideInAnyPackage("..primary..")
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage("..secondary..")
      .because("Primary should not interact with secondary")
      .check(importedClasses);
  }
}
