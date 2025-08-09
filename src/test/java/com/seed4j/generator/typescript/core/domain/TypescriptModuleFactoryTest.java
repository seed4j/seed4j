package com.seed4j.generator.typescript.core.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.packageJsonFile;
import static org.mockito.Mockito.verify;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class TypescriptModuleFactoryTest {

  @InjectMocks
  private TypescriptModuleFactory factory;

  @Mock
  private NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

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
      .containing(nodeDependency("vite-tsconfig-paths"))
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
    verify(nodeLazyPackagesInstaller).runInstallIn(properties.projectFolder(), properties.nodePackageManager());
  }

  @Test
  void shouldBuildModuleWithPnpm() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .nodePackageManager(NodePackageManager.PNPM)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile()).hasFile("package.json").containing(nodeScript("test", "pnpm run watch:test"));
  }
}
