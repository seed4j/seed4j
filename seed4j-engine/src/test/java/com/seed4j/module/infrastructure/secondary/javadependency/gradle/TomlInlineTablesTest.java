package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class TomlInlineTablesTest {

  @Test
  void shouldSpaceInlineTablesBraces() {
    assertThat(
      TomlInlineTables.withBraceSpaces("assertj = {module = \"org.assertj:assertj-core\", version.ref = \"assertj\"}\n")
    ).isEqualTo("assertj = { module = \"org.assertj:assertj-core\", version.ref = \"assertj\" }\n");
  }

  @Test
  void shouldSpaceNestedInlineTablesBraces() {
    assertThat(TomlInlineTables.withBraceSpaces("assertj = {module = \"g:a\", version = {strictly = \"1.0\"}}")).isEqualTo(
      "assertj = { module = \"g:a\", version = { strictly = \"1.0\" } }"
    );
  }

  @Test
  void shouldKeepEmptyInlineTables() {
    assertThat(TomlInlineTables.withBraceSpaces("empty = {}")).isEqualTo("empty = {}");
  }

  @Test
  void shouldKeepBracesInStrings() {
    assertThat(TomlInlineTables.withBraceSpaces("braces = {value = \"{a} \\\"{b}\\\"\"}")).isEqualTo(
      "braces = { value = \"{a} \\\"{b}\\\"\" }"
    );
  }
}
