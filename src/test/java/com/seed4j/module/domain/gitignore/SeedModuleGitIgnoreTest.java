package com.seed4j.module.domain.gitignore;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static com.seed4j.module.domain.SeedModule.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedModuleGitIgnoreTest {

  @Test
  void hasSimpleString() {
    SeedModuleGitIgnore gitIgnore = SeedModuleGitIgnore.builder(moduleBuilder(allProperties()))
      .comment("A comment")
      .pattern("target/")
      .build();

    assertThat(gitIgnore).hasToString("[# A comment, target/]");
  }
}
