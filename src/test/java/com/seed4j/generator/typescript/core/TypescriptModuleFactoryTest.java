package com.seed4j.generator.typescript.core;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.packageJsonFile;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices.FakeNodeLazyPackagesInstaller.Installation;
import org.junit.jupiter.api.Test;

@UnitTest
class TypescriptModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final TypescriptModuleFactory factory = new TypescriptModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
      .containing("\"type\": \"module\"")
      .containing(nodeDependency("typescript"))
      .containing(nodeDependency("@tsconfig/recommended"))
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@typescript-eslint/parser"))
      .containing(nodeDependency("@vitest/coverage-istanbul"))
      .containing(nodeDependency("eslint"))
      .containing(nodeDependency("eslint-config-prettier"))
      .containing(nodeDependency("@eslint/js"))
      .containing(nodeDependency("globals"))
      .containing(nodeDependency("typescript-eslint"))
      .containing(nodeDependency("vitest"))
      .containing(nodeDependency("vitest-sonar-reporter"))
      .containing(nodeScript("test", "npm run watch:test"))
      .containing(nodeScript("test:coverage", "vitest run --coverage"))
      .containing(nodeScript("watch", "npm-run-all --parallel watch:*"))
      .containing(nodeScript("watch:test", "vitest --"))
      .containing(nodeScript("watch:tsc", "tsc --noEmit --watch"))
      .containing(nodeScript("lint", "eslint ."))
      .and()
      .hasPrefixedFiles("", "eslint.config.js", "tsconfig.json");
    assertThat(seed4JModuleServices.nodeLazyPackagesInstaller().installations()).containsExactly(
      new Installation(properties.projectFolder(), properties.nodePackageManager())
    );
  }

  @Test
  void shouldBuildModuleWithPnpm() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .nodePackageManager(NodePackageManager.PNPM)
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile()).hasFile("package.json").containing(nodeScript("test", "pnpm run watch:test"));
  }
}
