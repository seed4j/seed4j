package com.seed4j;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@UnitTest
class HexagonalArchTest {

  private static final String ROOT_PACKAGE = "com.seed4j";
  private static final String WIRE_PACKAGES = ROOT_PACKAGE.concat(".wire..");

  // only the engine and the server are made of business contexts and shared kernels
  private static final List<Path> CONTEXTS_MODULES = Stream.of(".", "../seed4j-engine").map(Path::of).toList();

  private static final JavaClasses classes = new ClassFileImporter()
    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
    .importPackages(ROOT_PACKAGE);

  private static final JavaClasses contextsModulesClasses = new ClassFileImporter().importPaths(
    CONTEXTS_MODULES.stream()
      .map(module -> module.resolve("target").resolve("classes"))
      .toList()
  );

  private static final Collection<String> businessContexts = packagesWithAnnotation(BusinessContext.class);
  private static final Collection<String> businessContextsPackages = buildPackagesPatterns(businessContexts);

  private static final Collection<String> sharedKernels = packagesWithAnnotation(SharedKernel.class);
  private static final Collection<String> sharedKernelsPackages = buildPackagesPatterns(sharedKernels);

  // the empty package is related to: https://github.com/TNG/ArchUnit/issues/191#issuecomment-507964792
  private static final Collection<String> vanillaPackages = List.of("java..", "");
  private static final Collection<String> commonToolsAndUtilsPackages = List.of(
    "org.slf4j..",
    "org.apache.commons..",
    "org.jspecify.annotations..",
    "com.uber.nullaway.annotations..",
    "com.google.guava.."
  );

  private static Collection<String> buildPackagesPatterns(Collection<String> packages) {
    return packages
      .stream()
      .map(path -> path + "..")
      .toList();
  }

  private static Collection<String> packagesWithAnnotation(Class<? extends Annotation> annotationClass) throws AssertionError {
    return CONTEXTS_MODULES.stream()
      .map(module -> module.resolve("src").resolve("main").resolve("java"))
      .flatMap(HexagonalArchTest::packageInfos)
      .map(toPackage())
      .filter(pack -> pack.getAnnotation(annotationClass) != null)
      .map(Package::getName)
      .toList();
  }

  private static Stream<String> packageInfos(Path sourceRoot) {
    try (Stream<Path> files = Files.walk(sourceRoot.resolve(ROOT_PACKAGE.replace('.', File.separatorChar)))) {
      return files
        .filter(path -> path.getFileName().toString().equals("package-info.java"))
        .map(path -> sourceRoot.relativize(path.getParent()).toString().replace(File.separatorChar, '.') + ".package-info")
        .toList()
        .stream();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private static Function<String, Package> toPackage() {
    return path -> {
      try {
        return Class.forName(path).getPackage();
      } catch (ClassNotFoundException _) {
        throw new AssertionError();
      }
    };
  }

  @Nested
  class BoundedContexts {

    @Test
    void shouldNotDependOnOtherBoundedContextDomains() {
      Stream.concat(businessContexts.stream(), sharedKernels.stream()).forEach(context ->
        noClasses()
          .that()
          .resideInAnyPackage(context + "..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(otherBusinessContextsDomains(context))
          .because("Contexts can only depend on classes in the same context or shared kernels")
          .check(classes)
      );
    }

    @Test
    void shouldBeAnHexagonalArchitecture() {
      Stream.concat(businessContexts.stream(), sharedKernels.stream()).forEach(context ->
        Architectures.layeredArchitecture()
          .consideringOnlyDependenciesInAnyPackage(context + "..")
          .withOptionalLayers(true)
          .layer("domain models")
          .definedBy(context + ".domain..")
          .layer("domain services")
          .definedBy(context + ".domain..")
          .layer("application services")
          .definedBy(context + ".application..")
          .layer("primary adapters")
          .definedBy(context + ".infrastructure.primary..")
          .layer("secondary adapters")
          .definedBy(context + ".infrastructure.secondary..")
          .whereLayer("application services")
          .mayOnlyBeAccessedByLayers("primary adapters")
          .whereLayer("primary adapters")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("secondary adapters")
          .mayNotBeAccessedByAnyLayer()
          .because("Each bounded context should implement an hexagonal architecture")
          .check(classes)
      );
    }

    @Test
    void primaryJavaAdaptersShouldOnlyBeCalledFromSecondaries() {
      classes()
        .that()
        .resideInAPackage("..primary..")
        .and()
        .areMetaAnnotatedWith(Component.class)
        .and()
        .haveSimpleNameStartingWith("Java")
        .should()
        .onlyHaveDependentClassesThat()
        .resideInAPackage("..secondary..")
        .because(
          "To interact between two contexts, secondary from context 'A' should call a primary Java adapter (naming convention starting with 'Java') from context 'B'"
        )
        .check(classes);
    }

    private String[] otherBusinessContextsDomains(String context) {
      return businessContexts
        .stream()
        .filter(other -> !context.equals(other))
        .map(name -> name + ".domain..")
        .toArray(String[]::new);
    }
  }

  @Nested
  class Domain {

    @Test
    void shouldNotDependOnOutside() {
      classes()
        .that()
        .resideInAPackage("..domain..")
        .should()
        .onlyDependOnClassesThat()
        .resideInAnyPackage(authorizedDomainPackages())
        .because("Domain model should only depend on domains and a very limited set of external dependencies")
        .check(classes);
    }

    private String[] authorizedDomainPackages() {
      return Stream.of(List.of("..domain.."), vanillaPackages, commonToolsAndUtilsPackages, sharedKernelsPackages)
        .flatMap(Collection::stream)
        .toArray(String[]::new);
    }
  }

  @Nested
  class Application {

    @Test
    void shouldNotDependOnInfrastructure() {
      noClasses()
        .that()
        .resideInAPackage("..application..")
        .should()
        .dependOnClassesThat()
        .resideInAPackage("..infrastructure..")
        .because("Application should only depend on domain, not on infrastructure")
        .check(classes);
    }
  }

  @Nested
  class Primary {

    @Test
    void shouldNotDependOnSecondary() {
      noClasses()
        .that()
        .resideInAPackage("..primary..")
        .should()
        .dependOnClassesThat()
        .resideInAPackage("..secondary..")
        .because("Primary should not interact with secondary")
        .check(classes);
    }

    @Test
    void shouldNotHavePublicControllers() {
      noClasses().that().areMetaAnnotatedWith(Controller.class).should().bePublic().check(classes);
    }
  }

  @Nested
  class Secondary {

    @Test
    void shouldNotDependOnApplication() {
      noClasses()
        .that()
        .resideInAPackage("..infrastructure.secondary..")
        .should()
        .dependOnClassesThat()
        .resideInAPackage("..application..")
        .because("Secondary should not depend on application")
        .check(classes);
    }

    @Test
    void shouldNotDependOnSameContextPrimary() {
      Stream.concat(businessContexts.stream(), sharedKernels.stream()).forEach(context ->
        noClasses()
          .that()
          .resideInAPackage(context + ".infrastructure.secondary..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage(context + ".infrastructure.primary")
          .because("Secondary should not loop to its own context's primary")
          .check(classes)
      );
    }
  }

  @Nested
  class SharedKernels {

    @Test
    void sharedPackageShouldOnlyContainSharedKernels() {
      classes()
        .that()
        .haveSimpleName("package-info")
        .and()
        .resideInAPackage(ROOT_PACKAGE.concat(".shared.."))
        .and()
        .resideOutsideOfPackages(ROOT_PACKAGE.concat(".shared..domain"), ROOT_PACKAGE.concat(".shared..infrastructure.*"))
        .should()
        .beMetaAnnotatedWith(SharedKernel.class)
        .because(ROOT_PACKAGE + ".shared package should only contain shared kernels")
        .check(contextsModulesClasses);
    }
  }

  @Nested
  class Wire {

    @Test
    void shouldNotDependOnBoundedContextsOrSharedKernels() {
      noClasses()
        .that()
        .resideInAPackage(WIRE_PACKAGES)
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(businessContextsOrSharedKernelsPackages())
        .because("Wire should not depend on business contexts or shared kernel should not depend")
        .check(classes);
    }

    @Test
    void boundedContextsAndSharedKernelsShouldNotDependOnWire() {
      noClasses()
        .that()
        .resideInAnyPackage(businessContextsOrSharedKernelsPackages())
        .should()
        .dependOnClassesThat()
        .resideInAPackage(WIRE_PACKAGES)
        .because("Business contexts and shared kernel should not depend on wire")
        .check(classes);
    }

    private static String[] businessContextsOrSharedKernelsPackages() {
      return Stream.of(businessContextsPackages, sharedKernelsPackages)
        .flatMap(Collection::stream)
        .toArray(String[]::new);
    }

    @Test
    void shouldNotHavePublicClasses() {
      noClasses().that().resideInAnyPackage(WIRE_PACKAGES).should().bePublic().check(classes);
    }
  }
}
