package com.seed4j.generator.ci.gitlab.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GitLabCiModuleFactoryTest {

  private static final GitLabCiModuleFactory factory = new GitLabCiModuleFactory();

  @Test
  void shouldBuildGitLabCiMavenModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildGitLabCiMavenModule(properties);

    assertThatModule(module).hasFiles(".gitlab-ci.yml");
  }

  @Test
  void shouldBuildGitLabCiGradleModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildGitLabCiGradleModule(properties);

    assertThatModule(module).hasFiles(".gitlab-ci.yml");
  }
}
