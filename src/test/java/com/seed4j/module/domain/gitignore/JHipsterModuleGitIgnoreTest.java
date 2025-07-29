package com.seed4j.module.domain.gitignore;

import static com.seed4j.module.domain.JHipsterModule.*;
import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterModuleGitIgnoreTest {

  @Test
  void hasSimpleString() {
    JHipsterModuleGitIgnore gitIgnore = JHipsterModuleGitIgnore.builder(moduleBuilder(allProperties()))
      .comment("A comment")
      .pattern("target/")
      .build();

    assertThat(gitIgnore).hasToString("[# A comment, target/]");
  }
}
