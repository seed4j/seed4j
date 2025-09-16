package com.seed4j.generator.ci.gitlab.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GitLabCiModuleFactoryTest {

  private static final GitLabCiModuleFactory factory = new GitLabCiModuleFactory();

  @Test
  void shouldBuildGitLabCiMavenModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildGitLabCiMavenModule(properties);

    assertThatModule(module).hasFiles(".gitlab-ci.yml");
  }

  @Test
  void shouldBuildGitLabCiGradleModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildGitLabCiGradleModule(properties);

    assertThatModule(module).hasFiles(".gitlab-ci.yml");
  }
}
