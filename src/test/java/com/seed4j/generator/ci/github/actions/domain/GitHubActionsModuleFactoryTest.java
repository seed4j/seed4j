package com.seed4j.generator.ci.github.actions.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodePackageVersion;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
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
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    mockNodeVersion();

    Seed4JModule module = factory.buildGitHubActionsMavenModule(properties);

    assertThatModuleWithFiles(module, pomFile()).hasFile(".github/workflows/github-actions.yml").matchingSavedSnapshot();
  }

  private void mockNodeVersion() {
    when(nodeVersions.nodeVersion()).thenReturn(new NodePackageVersion("44"));
  }

  @Test
  void shouldBuildGitHubActionsGradleModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    mockNodeVersion();

    Seed4JModule module = factory.buildGitHubActionsGradleModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile()).hasFile(".github/workflows/github-actions.yml").matchingSavedSnapshot();
  }
}
