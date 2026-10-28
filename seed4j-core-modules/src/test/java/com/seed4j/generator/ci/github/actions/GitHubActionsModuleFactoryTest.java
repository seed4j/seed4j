package com.seed4j.generator.ci.github.actions;

import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodePackage;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final GitHubActionsModuleFactory factory = new GitHubActionsModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildGitHubActionsMavenModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    mockNodeVersion();

    Seed4JModule module = factory.buildGitHubActionsMavenModule(properties);

    assertThatModuleWithFiles(module, pomFile()).hasFile(".github/workflows/github-actions.yml").matchingSavedSnapshot();
  }

  private void mockNodeVersion() {
    seed4JModuleServices.nodeVersions().put(COMMON.build(), new NodePackage("node", "44"));
  }

  @Test
  void shouldBuildGitHubActionsGradleModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    mockNodeVersion();

    Seed4JModule module = factory.buildGitHubActionsGradleModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile()).hasFile(".github/workflows/github-actions.yml").matchingSavedSnapshot();
  }
}
