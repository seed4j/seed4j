package com.seed4j;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import org.junit.jupiter.api.Test;

@UnitTest
class EqualsHashcodeArchTest {

  private static final String ROOT_PACKAGE = "com.seed4j..";

  private static final JavaClasses classes = new ClassFileImporter()
    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
    .importPackages(ROOT_PACKAGE);

  @Test
  void shouldHaveValidEqualsHashcodeContract() {
    classes().that().resideInAnyPackage(ROOT_PACKAGE).and().areNotInterfaces().should(implementBothOrNone()).check(classes);
  }

  private ArchCondition.ConditionByPredicate<JavaClass> implementBothOrNone() {
    return ArchCondition.from(
      new DescribedPredicate<>("Class should implement none or both methods equals and hashcode") {
        @Override
        public boolean test(JavaClass clazz) {
          return hasEquals(clazz) == hasHashCode(clazz);
        }

        private boolean hasHashCode(JavaClass clazz) {
          return clazz.getMethods().stream().anyMatch(this::isMethodHashCode);
        }

        private boolean isMethodHashCode(JavaMethod method) {
          return method.getName().equals("hashCode") && method.getParameters().isEmpty();
        }

        private boolean hasEquals(JavaClass clazz) {
          return clazz.getMethods().stream().anyMatch(this::isMethodEquals);
        }

        private boolean isMethodEquals(JavaMethod method) {
          return method.getName().equals("equals") && hasOneObjectParameter(method);
        }

        private boolean hasOneObjectParameter(JavaMethod method) {
          return method.getParameters().size() == 1 && method.getParameters().getFirst().getRawType().isAssignableFrom(Object.class);
        }
      }
    );
  }
}
