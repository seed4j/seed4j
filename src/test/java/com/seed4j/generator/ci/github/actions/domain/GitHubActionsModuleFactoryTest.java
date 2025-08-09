package com.seed4j.generator.ci.github.actions.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.nodejs.NodePackageVersion;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GitHubActionsModuleFactoryTest {

  @Mock
  private NodeVersions nodeVersions;

  @InjectMocks
  private GitHubActionsModuleFactory factory;

  @Test
  void shouldBuildGitHubActionsMavenModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    mockNodeVersion();

    JHipsterModule module = factory.buildGitHubActionsMavenModule(properties);

    assertThatModuleWithFiles(module, pomFile()).hasFile(".github/workflows/github-actions.yml").matchingSavedSnapshot();
  }

  private void mockNodeVersion() {
    when(nodeVersions.nodeVersion()).thenReturn(new NodePackageVersion("44"));
  }

  @Test
  void shouldBuildGitHubActionsGradleModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    mockNodeVersion();

    JHipsterModule module = factory.buildGitHubActionsGradleModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile()).hasFile(".github/workflows/github-actions.yml").matchingSavedSnapshot();
  }
}
