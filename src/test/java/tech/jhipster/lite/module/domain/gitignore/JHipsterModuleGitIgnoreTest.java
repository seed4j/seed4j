package tech.jhipster.lite.module.domain.gitignore;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

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
