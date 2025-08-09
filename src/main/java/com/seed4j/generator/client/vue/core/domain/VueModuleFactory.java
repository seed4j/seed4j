package com.seed4j.generator.client.vue.core.domain;

import static com.seed4j.generator.typescript.common.domain.EslintShortcuts.eslintTypescriptRule;
import static com.seed4j.generator.typescript.common.domain.TsConfigShortcuts.tsConfigCompilerOption;
import static com.seed4j.generator.typescript.common.domain.VitestShortcuts.vitestCoverageExclusion;
import static com.seed4j.module.domain.SeedModule.*;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.VUE;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import java.util.function.Consumer;

public class VueModuleFactory {

  private static final SeedSource SOURCE = from("client/vue");
  private static final SeedSource APP_SOURCE = from("client/vue/webapp/app");
  private static final SeedSource PIQURE_SOURCE = from("client/common/piqure");
  private static final SeedSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");
  private static final SeedSource IMAGE_SOURCE = SOURCE.append("webapp/content/images");

  private static final SeedDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final SeedDestination TEST_DESTINATION = to("src/test/webapp");

  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  public VueModuleFactory(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.nodeLazyPackagesInstaller = nodeLazyPackagesInstaller;
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .preCommitActions(stagedFilesFilter("{src/**/,}*.{ts,vue}"), preCommitCommands("eslint --fix", "prettier --write"))
      .documentation(documentationTitle("Vue"), DOCUMENTATION_SOURCE.file("vue.md"))
      .packageJson()
        .removeDevDependency(packageName("@tsconfig/recommended"))
        .addDependency(packageName("vue"), VUE)
        .addDependency(packageName("axios"), VUE)
        .addDependency(packageName("piqure"), COMMON)
        .addDevDependency(packageName("@vitejs/plugin-vue"), VUE)
        .addDevDependency(packageName("@vue/test-utils"), VUE)
        .addDevDependency(packageName("@vue/tsconfig"), VUE)
        .addDevDependency(packageName("eslint-plugin-vue"), VUE)
        .addDevDependency(packageName("jsdom"), COMMON)
        .addDevDependency(packageName("vite"), COMMON)
        .addDevDependency(packageName("vue-tsc"), VUE)
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:tsc"), scriptCommand("vue-tsc -p tsconfig.build.json --noEmit"))
        .addScript(scriptKey("build:vite"), scriptCommand("vite build --emptyOutDir"))
        .addScript(scriptKey("dev"), scriptCommand("npm-run-all --parallel dev:*"))
        .addScript(scriptKey("dev:vite"), scriptCommand("vite"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .addScript(scriptKey("watch:tsc"), runScriptCommandWith(properties.nodePackageManager(), "build:tsc -- --watch"))
        .and()
      .postActions()
        .add(context -> nodeLazyPackagesInstaller.runInstallIn(context.projectFolder(), properties.nodePackageManager()))
        .and()
      .files()
        .add(SOURCE.file("tsconfig.build.json"), to("tsconfig.build.json"))
        .batch(SOURCE, to("."))
          .addTemplate("vite.config.ts")
          .and()
        .add(SOURCE.template("webapp/index.html"), to("src/main/webapp/index.html"))
        .batch(APP_SOURCE, MAIN_DESTINATION)
          .addTemplate("env.d.ts")
          .addTemplate("main.ts")
          .addTemplate("AppVue.vue")
          .and()
        .batch(PIQURE_SOURCE, MAIN_DESTINATION)
          .addTemplate("injections.ts")
        .and()
        .batch(APP_SOURCE.append("home"), MAIN_DESTINATION.append("home"))
          .addTemplate("infrastructure/primary/HomepageVue.vue")
          .and()
        .add(APP_SOURCE.template("shared/http/infrastructure/secondary/AxiosHttp.ts.mustache"), MAIN_DESTINATION.append("shared/http/infrastructure/secondary/AxiosHttp.ts"))
        .batch(IMAGE_SOURCE, to("src/main/webapp/content/images"))
          .addFile("JHipster-Lite-neon-green.png")
          .addFile("VueLogo.png")
          .and()
        .add(SOURCE.template("Dummy.spec.ts"), to("src/test/webapp/unit/Dummy.spec.ts"))
        .add(APP_SOURCE.template("test/webapp/unit/shared/http/infrastructure/secondary/AxiosHttp.spec.ts.mustache"), TEST_DESTINATION.append("unit/shared/http/infrastructure/secondary/AxiosHttp.spec.ts"))
        .add(APP_SOURCE.template("test/webapp/unit/shared/http/infrastructure/secondary/AxiosStub.ts.mustache"), TEST_DESTINATION.append("unit/shared/http/infrastructure/secondary/AxiosStub.ts"))
        .and()
      .apply(patchEslintConfig(properties))
      .apply(patchTsConfig(properties))
      .apply(patchVitestConfig())
      .build();
    // @formatter:on
  }

  private Consumer<SeedModuleBuilder> patchEslintConfig(SeedModuleProperties properties) {
    String vuePluginConfig = """
      \t...vue.configs['flat/recommended'],
      \t{
      \t\tfiles: ['**/*.vue'],
      \t\tlanguageOptions: {
      \t\t\tparserOptions: { parser: '@typescript-eslint/parser' },
      \t\t\tglobals: { ...globals.browser },
      \t\t},
      \t},\
      """.replace("\t", properties.indentation().spaces());
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("eslint.config.js"))
          .add(lineAfterRegex("from '@eslint/js'"), "import vue from 'eslint-plugin-vue';")
          .add(lineAfterRegex("...typescript.configs.recommended"), vuePluginConfig)
          .add(text("files: ['src/*/webapp/**/*.ts']"), "files: ['src/*/webapp/**/*.vue', 'src/*/webapp/**/*.ts']")
          .and()
        .and()
      .apply(eslintTypescriptRule("'vue/html-self-closing': 'off'", properties.indentation()))
      .apply(eslintTypescriptRule("'@typescript-eslint/no-explicit-any': 'off'", properties.indentation()))
      .apply(eslintTypescriptRule("'@typescript-eslint/no-empty-object-type': 'off'", properties.indentation()))
      .apply(eslintTypescriptRule("'@typescript-eslint/consistent-type-imports': 'error'", properties.indentation()));
    // @formatter:on
  }

  private Consumer<SeedModuleBuilder> patchTsConfig(SeedModuleProperties properties) {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("tsconfig.json"))
          .add(text("@tsconfig/recommended/tsconfig.json"), "@vue/tsconfig/tsconfig.dom.json")
          .add(text("\"types\": ["), "\"types\": [\"vite/client\", ")
          .and()
        .and()
      .apply(tsConfigCompilerOption("sourceMap", true, properties.indentation()))
      .apply(tsConfigCompilerOption("allowJs", true, properties.indentation()));
    // @formatter:on
  }

  private Consumer<SeedModuleBuilder> patchVitestConfig() {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("vitest.config.ts"))
          .add(lineAfterRegex("from 'vitest/config';"), "import vue from '@vitejs/plugin-vue';")
          .add(text("plugins: ["), "plugins: [vue(), ")
          .add(text("environment: 'node',"), "environment: 'jsdom',")
        .and()
      .and()
      .apply(vitestCoverageExclusion("src/main/webapp/**/*.component.ts"))
      .apply(vitestCoverageExclusion("src/main/webapp/app/injections.ts"))
      .apply(vitestCoverageExclusion("src/main/webapp/app/main.ts"));
    // @formatter:on
  }
}
