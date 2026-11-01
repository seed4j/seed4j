package com.seed4j.generator.client.svelte.core;

import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices.FakeNodeLazyPackagesInstaller.Installation;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.ModuleFile;
import org.junit.jupiter.api.Test;

@UnitTest
class SvelteModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final SvelteModuleFactory factory = new SvelteModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier(), tsConfigFile(), vitestConfigFile(), eslintConfigFile(), prettierConfigFile())
      .hasFile(".gitignore")
        .containing("""
          # Svelte
          .svelte-kit/\
          """)
        .and()
      .hasFile("package.json")
        .notContaining(nodeDependency("@tsconfig/recommended"))
        .notContaining(nodeDependency("svelte-navigator"))
        .notContaining(nodeDependency("svelte-preprocess"))
        .containing(nodeDependency("svelte"))
        .containing(nodeDependency("@sveltejs/kit"))
        .containing(nodeDependency("@sveltejs/adapter-static"))
        .containing(nodeDependency("@sveltejs/vite-plugin-svelte"))
        .containing(nodeDependency("@testing-library/svelte"))
        .containing(nodeDependency("eslint-plugin-svelte"))
        .containing(nodeDependency("svelte-check"))
        .containing(nodeDependency("prettier-plugin-svelte"))
        .containing(nodeDependency("vite"))
        .containing(nodeDependency("jsdom"))
        .containing(nodeScript("build", "npm-run-all build:*"))
        .containing(nodeScript("build:svelte-check", "svelte-kit sync && svelte-check --tsconfig ./tsconfig.json"))
        .containing(nodeScript("build:vite", "vite build"))
        .containing(nodeScript("dev", "npm-run-all --parallel dev:*"))
        .containing(nodeScript("dev:vite", "vite dev"))
        .containing(nodeScript("preview", "vite preview"))
        .containing(nodeScript("start", "vite dev"))
        .containing(nodeScript("watch:tsc", "svelte-check --tsconfig ./tsconfig.json --watch"))
        .containing(nodeScript("prepare", "svelte-kit sync && husky"))
        .and()
      .hasFile(".lintstagedrc.cjs")
        .containing("'{src/**/,}*.{ts,svelte}': ['eslint --fix', 'prettier --write']")
        .and()
      .hasFile("tsconfig.json")
        .matchingSavedSnapshot()
        .and()
      .hasFile("vitest.config.ts")
        .matchingSavedSnapshot()
        .and()
      .hasFile("eslint.config.js")
        .matchingSavedSnapshot()
        .and()
      .hasFile(".prettierrc")
        .matchingSavedSnapshot()
        .and()
      .hasFile("vite.config.ts")
        .containing("from 'vite'")
        .containing("plugins: [")
        .containing("port: 9000")
        .containing("pages: 'target/classes/static'");
    // @formatter:on
    assertThat(seed4JModuleServices.nodeLazyPackagesInstaller().installations()).containsExactly(
      new Installation(properties.projectFolder(), properties.nodePackageManager())
    );
  }

  @Test
  void shouldBuildModuleWithPnpm() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .nodePackageManager(PNPM)
      .build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier(), tsConfigFile(), vitestConfigFile(), eslintConfigFile(), prettierConfigFile())
      .hasFile("package.json")
        .containing(nodeScript("prepare", "svelte-kit sync && husky"));
    // @formatter:on
    assertThat(seed4JModuleServices.nodeLazyPackagesInstaller().installations()).containsExactly(
      new Installation(properties.projectFolder(), PNPM)
    );
  }

  @Test
  void shouldKeepProjectIndentationInPatches() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).put("indentSize", 4).build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier(), tsConfigFile(), vitestConfigFile(), eslintConfigFile(), prettierConfigFile())
      .hasFile("vitest.config.ts")
        .containing("\n    plugins: [sveltekit(svelteKitConfig), svelteTesting()],\n")
        .and()
      .hasFile("eslint.config.js")
        .containing("\n    ...svelte.configs.recommended,\n")
        .and()
      .hasFile(".prettierrc")
        .containing("plugins:\n  - prettier-plugin-svelte\n");
    // @formatter:on
  }

  private static ModuleFile prettierConfigFile() {
    return file("src/main/resources/generator/prettier/.prettierrc.mustache", ".prettierrc");
  }
}
