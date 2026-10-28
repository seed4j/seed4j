package com.seed4j;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

@UnitTest
class CoreModulesArchTest {

  private static final JavaClasses classes = new ClassFileImporter()
    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
    .importPackages("com.seed4j.generator");

  // a module is a package providing module resources, the other packages hold code shared by modules
  private static final Set<String> modules = classes
    .stream()
    .filter(javaClass -> javaClass.isAssignableTo(Seed4JModuleResourceProvider.class))
    .map(JavaClass::getPackageName)
    .collect(Collectors.toSet());

  private static final DescribedPredicate<JavaClass> inAModule = DescribedPredicate.describe("reside in a module", javaClass ->
    modules.contains(javaClass.getPackageName())
  );

  @Test
  void shouldNotDependOnOtherModules() {
    classes()
      .that(inAModule)
      .should(notDependOnAnotherModule())
      .because("A module can only depend on the code shared by modules, not on another module")
      .check(classes);
  }

  @Test
  void shouldNotExposeFactories() {
    classes()
      .that(inAModule)
      .and()
      .haveSimpleNameEndingWith("ModuleFactory")
      .should()
      .notBePublic()
      .because("A module is exposed by its resource providers, its factory is an implementation detail")
      .check(classes);
  }

  private static ArchCondition<JavaClass> notDependOnAnotherModule() {
    return new ArchCondition<>("not depend on another module") {
      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {
        javaClass
          .getDirectDependenciesFromSelf()
          .stream()
          .filter(dependency -> isAnotherModule(javaClass, dependency))
          .forEach(dependency -> events.add(SimpleConditionEvent.violated(dependency, dependency.getDescription())));
      }

      private boolean isAnotherModule(JavaClass javaClass, Dependency dependency) {
        String target = dependency.getTargetClass().getPackageName();

        return modules.contains(target) && !target.equals(javaClass.getPackageName());
      }
    };
  }
}
