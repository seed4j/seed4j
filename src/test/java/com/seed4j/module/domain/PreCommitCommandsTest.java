package com.seed4j.module.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class PreCommitCommandsTest {

  @Test
  void shouldHandleSingleCommand() {
    assertThat(PreCommitCommands.of("prettier --write").get()).isEqualTo("'prettier --write'");
  }

  @Test
  void shouldHandleSingleCommandAlreadyQuoted() {
    assertThat(PreCommitCommands.of("'prettier --write'").get()).isEqualTo("'prettier --write'");
  }

  @Test
  void shouldHandleSingleCommandAlreadyArray() {
    assertThat(PreCommitCommands.of("['prettier --write']").get()).isEqualTo("['prettier --write']");
  }

  @Test
  void shouldHandleMultipleCommands() {
    assertThat(PreCommitCommands.of("prettier --write", "eslint --fix").get()).isEqualTo("['prettier --write', 'eslint --fix']");
  }
}
