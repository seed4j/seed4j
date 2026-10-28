package com.seed4j.generator.init;

import static com.seed4j.module.domain.nodejs.NodePackageManager.NPM;
import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeScript;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodePackage;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import org.junit.jupiter.api.Test;

@UnitTest
class InitModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final InitModuleFactory factory = new InitModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildModule() {
    mockNodeVersion();
    mockNodePackageManagerVersion(NPM, "11.9.9");
    Seed4JModuleProperties properties = defaultProperties(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

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
    Seed4JModuleProperties properties = defaultProperties(TestFileUtils.tmpDirForTest()).nodePackageManager(NPM).build();

    Seed4JModule module = factory.buildModule(properties);

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
    Seed4JModuleProperties properties = defaultProperties(TestFileUtils.tmpDirForTest()).nodePackageManager(PNPM).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("README.md")
      .containing("pnpm install")
      .and()
      .hasFile("package.json")
      .containing("\"packageManager\": \"pnpm@9.9.9\"");
  }

  private void mockNodeVersion() {
    seed4JModuleServices.nodeVersions().put(COMMON.build(), new NodePackage("node", "16.0.0"));
  }

  private void mockNodePackageManagerVersion(NodePackageManager packageManager, String version) {
    seed4JModuleServices.nodeVersions().put(COMMON.build(), new NodePackage(packageManager.packageName(), version));
  }

  private static Seed4JModulesFixture.Seed4JModulePropertiesBuilder defaultProperties(String folder) {
    return Seed4JModulesFixture.propertiesBuilder(folder)
      .projectBaseName("testProject")
      .projectName("Test Project")
      .nodePackageManager(NPM)
      .put("endOfLine", "crlf")
      .put("indentSize", 4);
  }
}
