package com.seed4j;

import static org.assertj.core.api.Assertions.assertThat;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;

@UnitTest
class NativeHintsArchTest {

  private static final JavaClasses classes = new ClassFileImporter()
    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
    .importPackages("com.seed4j");

  @Test
  void shouldImportRuntimeHintsRegistrars() {
    Set<String> importedRegistrars = classes
      .stream()
      .filter(javaClass -> javaClass.isAnnotatedWith(ImportRuntimeHints.class))
      .flatMap(javaClass -> Arrays.stream(javaClass.reflect().getAnnotation(ImportRuntimeHints.class).value()))
      .map(Class::getName)
      .collect(Collectors.toSet());

    assertThat(
      classes
        .stream()
        .filter(javaClass -> javaClass.isAssignableTo(RuntimeHintsRegistrar.class))
        .map(JavaClass::getName)
    )
      .as("Runtime hints are only registered for the native image when an @ImportRuntimeHints imports them")
      .allMatch(importedRegistrars::contains);
  }
}
