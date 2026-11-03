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
      valueObject("Seed4JProjectFilesPaths", "paths", Seed4JProjectFilesPaths::new),
      valueObject("Seed4JDestinations", "destinations", Seed4JDestinations::new),
      valueObject("Seed4JFilesToDelete", "files", Seed4JFilesToDelete::new),
      valueObject("Seed4JFilesToMove", "files", Seed4JFilesToMove::new),
      valueObject("JavaBuildCommands", "commands", JavaBuildCommands::new),
      valueObject("PackageJsonDependencies", "dependencies", PackageJsonDependencies::new),
      valueObject("PackageNames", "packageNames", PackageNames::new),
      valueObject("Scripts", "scripts", Scripts::new),
      valueObject("Seed4JUpgradeFilesReplacements", "replacements", Seed4JUpgradeFilesReplacements::new),
      valueObject("Seed4JStartupCommands", "commands", Seed4JStartupCommands::new)
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
