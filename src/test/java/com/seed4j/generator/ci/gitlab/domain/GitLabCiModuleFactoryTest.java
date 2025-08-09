package com.seed4j.generator.ci.gitlab.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GitLabCiModuleFactoryTest {

  private static final GitLabCiModuleFactory factory = new GitLabCiModuleFactory();

  @Test
  void shouldBuildGitLabCiMavenModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildGitLabCiMavenModule(properties);

    assertThatModule(module).hasFiles(".gitlab-ci.yml");
  }

  @Test
  void shouldBuildGitLabCiGradleModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildGitLabCiGradleModule(properties);

    assertThatModule(module).hasFiles(".gitlab-ci.yml");
  }
}
