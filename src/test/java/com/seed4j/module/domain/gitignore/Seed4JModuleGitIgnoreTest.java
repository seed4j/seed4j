package com.seed4j.module.domain.gitignore;

import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.Seed4JModulesFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModuleGitIgnoreTest {

  @Test
  void hasSimpleString() {
    Seed4JModuleGitIgnore gitIgnore = Seed4JModuleGitIgnore.builder(moduleBuilder(allProperties()))
      .comment("A comment")
      .pattern("target/")
      .build();

    assertThat(gitIgnore).hasToString("[# A comment, target/]");
  }
}
