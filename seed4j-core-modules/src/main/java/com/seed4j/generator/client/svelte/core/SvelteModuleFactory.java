package com.seed4j.generator.client.svelte.core;

import static com.seed4j.generator.typescript.common.domain.VitestShortcuts.vitestCoverageExclusion;
import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.SVELTE;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;

class SvelteModuleFactory {

  private static final Seed4JSource SOURCE = from("client/svelte");

  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  SvelteModuleFactory(Seed4JModuleServices services) {
    this.nodeLazyPackagesInstaller = services.nodeLazyPackagesInstaller();
  }

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .preCommitActions(stagedFilesFilter("{src/**/,}*.{ts,svelte}"), preCommitCommands("eslint --fix", "prettier --write"))
      .gitIgnore()
        .comment("Svelte")
        .pattern(".svelte-kit/")
        .and()
      .packageJson()
        .removeDevDependency(packageName("@tsconfig/recommended"))
        .addDevDependency(packageName("svelte"), SVELTE)
        .addDevDependency(packageName("@sveltejs/adapter-static"), SVELTE)
        .addDevDependency(packageName("@sveltejs/kit"), SVELTE)
        .addDevDependency(packageName("@sveltejs/vite-plugin-svelte"), SVELTE)
        .addDevDependency(packageName("@testing-library/svelte"), SVELTE)
        .addDevDependency(packageName("eslint-plugin-svelte"), SVELTE)
        .addDevDependency(packageName("prettier-plugin-svelte"), SVELTE)
        .addDevDependency(packageName("svelte-check"), SVELTE)
        .addDevDependency(packageName("jsdom"), COMMON)
        .addDevDependency(packageName("vite"), COMMON)
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:svelte-check"), scriptCommand("svelte-kit sync && svelte-check --tsconfig ./tsconfig.json"))
        .addScript(scriptKey("build:vite"), scriptCommand("vite build"))
        .addScript(scriptKey("dev"), scriptCommand("npm-run-all --parallel dev:*"))
        .addScript(scriptKey("dev:vite"), scriptCommand("vite dev"))
        .addScript(scriptKey("prepare"), scriptCommand("svelte-kit sync && husky"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite dev"))
        .addScript(scriptKey("watch:tsc"), scriptCommand("svelte-check --tsconfig ./tsconfig.json --watch"))
        .and()
      .postActions()
        .add(context -> nodeLazyPackagesInstaller.runInstallIn(context.projectFolder(), properties.nodePackageManager()))
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addTemplate("vite.config.ts")
          .and()
        .and()
      .with(patchEslintConfig(properties))
      .with(patchTsConfig())
      .with(patchVitestConfig(properties))
      .with(patchPrettierConfig())
      .build();
    // @formatter:on
  }

  private Consumer<Seed4JModuleBuilder> patchEslintConfig(Seed4JModuleProperties properties) {
    String sveltePluginConfig = """
    \t...svelte.configs.recommended,
    \t{
    \t\tfiles: ['**/*.svelte', '**/*.svelte.ts'],
    \t\tlanguageOptions: {
    \t\t\tparserOptions: { parser: typescript.parser },
    \t\t\tglobals: { ...globals.browser },
    \t\t},
    \t},\
    """.replace("\t", properties.indentation().spaces());
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("eslint.config.js"))
          .add(lineAfterRegex("from '@eslint/js'"), "import svelte from 'eslint-plugin-svelte';")
          .add(lineAfterRegex("...typescript.configs.recommended"), sveltePluginConfig)
          .add(text("ignores: ["), "ignores: ['.svelte-kit/', ")
          .and()
        .and();
    // @formatter:on
  }

  private Consumer<Seed4JModuleBuilder> patchTsConfig() {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("tsconfig.json"))
          .add(text("@tsconfig/recommended/tsconfig.json"), "$app/tsconfig")
          .add(text("\"types\": ["), "\"types\": [\"$app/types\", \"vite/client\", ")
          .and()
        .and();
    // @formatter:on
  }

  private Consumer<Seed4JModuleBuilder> patchVitestConfig(Seed4JModuleProperties properties) {
    String imports = """
    import { sveltekit } from '@sveltejs/kit/vite';
    import { svelteTesting } from '@testing-library/svelte/vite';
    import { svelteKitConfig, webappAlias } from './vite.config.ts';\
    """;
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("vitest.config.ts"))
          .add(lineAfterRegex("from 'vitest/config';"), imports)
          .add(lineBeforeText("resolve: {"), properties.indentation().spaces() + "plugins: [sveltekit(svelteKitConfig), svelteTesting()],")
          .add(text("tsconfigPaths: true,"), "alias: webappAlias,")
          .add(text("environment: 'node',"), "environment: 'jsdom',")
          .add(text("'src/main/webapp/**/*.ts?(x)'"), "'src/main/webapp/**/*.ts?(x)', 'src/main/webapp/**/*.svelte'")
          .and()
        .and()
      .with(vitestCoverageExclusion("src/main/webapp/routes/**"));
    // @formatter:on
  }

  private Consumer<Seed4JModuleBuilder> patchPrettierConfig() {
    String svelteOverride = """
      - files: ['*.svelte']
        options:
          parser: svelte\
    """;
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path(".prettierrc"))
          .add(lineAfterText("plugins:"), "  - prettier-plugin-svelte")
          .add(lineAfterText("overrides:"), svelteOverride)
          .and()
        .and();
    // @formatter:on
  }
}
