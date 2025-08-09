package com.seed4j.generator.init.domain;

import static com.seed4j.module.domain.nodejs.NodePackageManager.NPM;
import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModule;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.nodeScript;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.nodejs.NodePackageManager;
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
class InitModuleFactoryTest {

  @Mock
  private NodeVersions nodeVersions;

  @InjectMocks
  private InitModuleFactory factory;

  @Test
  void shouldBuildModule() {
    mockNodeVersion();
    mockNodePackageManagerVersion(NPM, "11.9.9");
    SeedModuleProperties properties = defaultProperties(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("README.md")
      .containing("# Test Project")
      .and()
      .hasFiles(".gitignore", ".gitattributes")
      .hasFile(".editorconfig")
      .containing("end_of_line = crlf")
      .containing("indent_size = 4")
      .and()
      .hasFile("package.json")
      .containing("test-project")
      .containing("Test Project")
      .containing("\"node\": \">=16\"")
      .containing(nodeDependency("husky"))
      .containing(nodeDependency("lint-staged"))
      .containing(nodeScript("prepare", "husky"))
      .and()
      .hasFile(".lintstagedrc.cjs")
      .and()
      .hasExecutableFiles(".husky/pre-commit")
      .hasFile(".npmrc");
  }

  @Test
  void shouldBuildModuleForNpm() {
    mockNodeVersion();
    mockNodePackageManagerVersion(NPM, "11.9.9");
    SeedModuleProperties properties = defaultProperties(TestFileUtils.tmpDirForTest()).nodePackageManager(NPM).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("README.md")
      .containing("npm install")
      .and()
      .hasFile("package.json")
      .containing("\"packageManager\": \"npm@11.9.9\"");
  }

  @Test
  void shouldBuildModuleForPnpm() {
    mockNodeVersion();
    mockNodePackageManagerVersion(PNPM, "9.9.9");
    SeedModuleProperties properties = defaultProperties(TestFileUtils.tmpDirForTest()).nodePackageManager(PNPM).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("README.md")
      .containing("pnpm install")
      .and()
      .hasFile("package.json")
      .containing("\"packageManager\": \"pnpm@9.9.9\"");
  }

  private void mockNodeVersion() {
    when(nodeVersions.nodeVersion()).thenReturn(new NodePackageVersion("16.0.0"));
  }

  private void mockNodePackageManagerVersion(NodePackageManager packageManager, String version) {
    when(nodeVersions.packageManagerVersion(packageManager)).thenReturn(new NodePackageVersion(version));
  }

  private static SeedModulesFixture.JHipsterModulePropertiesBuilder defaultProperties(String folder) {
    return SeedModulesFixture.propertiesBuilder(folder)
      .projectBaseName("testProject")
      .projectName("Test Project")
      .nodePackageManager(NPM)
      .put("endOfLine", "crlf")
      .put("indentSize", 4);
  }
}
