package com.seed4j.module.domain.nodejs;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class NodePackageManagerTest {

  @Test
  void shouldComputeWindowsCommand() {
    assertThat(NodePackageManager.NPM.windowsCommand()).isEqualTo("npm.cmd");
  }
}
