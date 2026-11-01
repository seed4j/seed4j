package com.seed4j.module.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.file.Seed4JDestinations;
import com.seed4j.module.domain.file.Seed4JFilesToDelete;
import com.seed4j.module.domain.file.Seed4JFilesToMove;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.packagejson.PackageJsonDependencies;
import com.seed4j.module.domain.packagejson.PackageNames;
import com.seed4j.module.domain.packagejson.Scripts;
import com.seed4j.module.domain.replacement.Seed4JUpgradeFilesReplacements;
import com.seed4j.module.domain.startupcommand.Seed4JStartupCommands;
import com.seed4j.shared.error.domain.MissingMandatoryValueException;
import com.seed4j.shared.error.domain.NullElementInCollectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
@SuppressWarnings({ "unchecked", "rawtypes", "NullAway" })
class CollectionsValueObjectsTest {

  @ParameterizedTest(name = "{0}")
  @MethodSource("valueObjects")
  void shouldNotBuildWithoutCollection(String valueObject, String field, ValueObjectFactory factory) {
    assertThatThrownBy(() -> factory.build(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining(field);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("valueObjects")
  void shouldNotBuildWithNullElement(String valueObject, String field, ValueObjectFactory factory) {
    assertThatThrownBy(() -> factory.build(withNullElement()))
      .isExactlyInstanceOf(NullElementInCollectionException.class)
      .hasMessageContaining(field);
  }

  private static Stream<Arguments> valueObjects() {
    return Stream.of(
      valueObject("Seed4JProjectFilesPaths", "paths", values -> new Seed4JProjectFilesPaths(values)),
      valueObject("Seed4JDestinations", "destinations", values -> new Seed4JDestinations(values)),
      valueObject("Seed4JFilesToDelete", "files", values -> new Seed4JFilesToDelete(values)),
      valueObject("Seed4JFilesToMove", "files", values -> new Seed4JFilesToMove(values)),
      valueObject("JavaBuildCommands", "commands", values -> new JavaBuildCommands(values)),
      valueObject("PackageJsonDependencies", "dependencies", values -> new PackageJsonDependencies(values)),
      valueObject("PackageNames", "packageNames", values -> new PackageNames(values)),
      valueObject("Scripts", "scripts", values -> new Scripts(values)),
      valueObject("Seed4JUpgradeFilesReplacements", "replacements", values -> new Seed4JUpgradeFilesReplacements(values)),
      valueObject("Seed4JStartupCommands", "commands", values -> new Seed4JStartupCommands(values))
    );
  }

  private static List<Object> withNullElement() {
    List<Object> values = new ArrayList<>();
    values.add(null);

    return values;
  }

  private static Arguments valueObject(String valueObject, String field, ValueObjectFactory factory) {
    return Arguments.of(valueObject, field, factory);
  }

  @FunctionalInterface
  private interface ValueObjectFactory {
    void build(List values);
  }
}
