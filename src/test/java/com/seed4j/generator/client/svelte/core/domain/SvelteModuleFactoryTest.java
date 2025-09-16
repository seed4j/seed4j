package com.seed4j.generator.client.svelte.core.domain;

import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.lintStagedConfigFileWithPrettier;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.packageJsonFile;
import static org.mockito.Mockito.verify;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SvelteModuleFactoryTest {

  @InjectMocks
  private SvelteModuleFactory factory;

  @Mock
  private NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier())
      .hasFile(".gitignore")
        .containing("""
          # Svelte
          .svelte-kit/\
          """)
        .and()
      .hasFile("package.json")
        .containing(nodeDependency("svelte-navigator"))
        .containing(nodeDependency("@babel/preset-env"))
        .containing(nodeDependency("@sveltejs/adapter-static"))
        .containing(nodeDependency("@sveltejs/kit"))
        .containing(nodeDependency("@testing-library/svelte"))
        .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
        .containing(nodeDependency("@typescript-eslint/parser"))
        .containing(nodeDependency("@vitest/coverage-istanbul"))
        .containing(nodeDependency("babel-plugin-transform-vite-meta-env"))
        .containing(nodeDependency("eslint"))
        .containing(nodeDependency("eslint-config-prettier"))
        .containing(nodeDependency("eslint-plugin-svelte3"))
        .containing(nodeDependency("jsdom"))
        .containing(nodeDependency("prettier"))
        .containing(nodeDependency("prettier-plugin-svelte"))
        .containing(nodeDependency("svelte"))
        .containing(nodeDependency("svelte-check"))
        .containing(nodeDependency("svelte-preprocess"))
        .containing(nodeDependency("tslib"))
        .containing(nodeDependency("typescript"))
        .containing(nodeDependency("vite"))
        .containing(nodeDependency("vitest"))
        .containing(nodeDependency("vitest-sonar-reporter"))
        .containing(nodeScript("dev", "vite dev --port 9000"))
        .containing(nodeScript("start", "vite dev --port 9000"))
        .containing(nodeScript("build", "vite build"))
        .containing(nodeScript("package", "vite package"))
        .containing(nodeScript("preview", "vite preview"))
        .containing(nodeScript("check", "svelte-check --tsconfig ./tsconfig.json"))
        .containing(nodeScript("check:watch", "svelte-check --tsconfig ./tsconfig.json --watch"))
        .containing(nodeScript("lint", "prettier --ignore-path .gitignore --check && eslint --ignore-path .gitignore ."))
        .containing(nodeScript("format", "prettier --ignore-path .gitignore --write"))
        .containing(nodeScript("test", "npm run test:watch"))
        .containing(nodeScript("test:coverage", "vitest run --coverage"))
        .containing(nodeScript("test:watch", "vitest --"))
        .containing("\"type\": \"module\"")
        .and()
      .hasFiles(".eslintignore", ".eslintrc.cjs", ".npmrc", "tsconfig.json", "svelte.config.js", "vite.config.js", "vitest.config.ts")
      .hasPrefixedFiles("src/main/webapp", "app.html", "app.d.ts")
      .hasPrefixedFiles("src/main/webapp/routes", "+page.svelte")
      .hasPrefixedFiles("src/test/webapp/unit/common/primary/app", "App.spec.ts")
      .hasPrefixedFiles("src/main/webapp/app/common/primary/app", "App.svelte")
      .hasPrefixedFiles("src/main/webapp/assets", "JHipster-Lite-neon-orange.png")
      .hasPrefixedFiles("src/main/webapp/assets", "svelte-logo.png");
    // @formatter:on
    verify(nodeLazyPackagesInstaller).runInstallIn(properties.projectFolder(), properties.nodePackageManager());
  }

  @Test
  void shouldBuildModuleWithPnpm() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .nodePackageManager(PNPM)
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier())
      .hasFile("package.json")
      .containing(nodeScript("test", "pnpm run test:watch"));
  }
}
