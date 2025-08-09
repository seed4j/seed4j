package com.seed4j.generator.client.react.core.domain;

import static com.seed4j.generator.typescript.common.domain.EslintShortcuts.eslintTypescriptRule;
import static com.seed4j.generator.typescript.common.domain.TsConfigShortcuts.tsConfigCompilerOption;
import static com.seed4j.generator.typescript.common.domain.VitestShortcuts.vitestCoverageExclusion;
import static com.seed4j.module.domain.SeedModule.LINE_BREAK;
import static com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.lineAfterRegex;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.packageName;
import static com.seed4j.module.domain.SeedModule.path;
import static com.seed4j.module.domain.SeedModule.preCommitCommands;
import static com.seed4j.module.domain.SeedModule.regex;
import static com.seed4j.module.domain.SeedModule.scriptCommand;
import static com.seed4j.module.domain.SeedModule.scriptKey;
import static com.seed4j.module.domain.SeedModule.stagedFilesFilter;
import static com.seed4j.module.domain.SeedModule.text;
import static com.seed4j.module.domain.SeedModule.to;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.REACT;
import static com.seed4j.module.domain.replacement.ReplacementCondition.always;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;

public class ReactModuleFactory {

  private static final SeedSource SOURCE = from("client/react/core");

  private static final SeedSource WEBAPP_SOURCE = SOURCE.append("src/main/webapp");
  private static final SeedDestination WEBAPP_DESTINATION = to("src/main/webapp");

  private static final SeedSource APP_SOURCE = WEBAPP_SOURCE.append("app");
  private static final SeedDestination APP_DESTINATION = WEBAPP_DESTINATION.append("app");

  private static final SeedSource PIQURE_SOURCE = from("client/common/piqure");

  private static final String PRIMARY_APP = "home/infrastructure/primary";
  private static final String ASSETS = "assets";

  private static final SeedSource PRIMARY_APP_SOURCE = APP_SOURCE.append(PRIMARY_APP);
  private static final SeedDestination PRIMARY_APP_DESTINATION = APP_DESTINATION.append(PRIMARY_APP);

  private static final String TEST_PRIMARY = "src/test/webapp/unit/home/infrastructure/primary";
  private static final String DEFAULT_TSCONFIG_PATH = "\"@/*\": [\"src/main/webapp/app/*\"]";

  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  public ReactModuleFactory(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.nodeLazyPackagesInstaller = nodeLazyPackagesInstaller;
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .preCommitActions(stagedFilesFilter("{src/**/,}*.{ts,tsx}"), preCommitCommands("eslint --fix", "prettier --write"))
      .packageJson()
        .removeDevDependency(packageName("@tsconfig/recommended"))
        .addDevDependency(packageName("@testing-library/dom"), REACT)
        .addDevDependency(packageName("@testing-library/react"), REACT)
        .addDevDependency(packageName("@types/node"), COMMON)
        .addDevDependency(packageName("@types/react"), REACT)
        .addDevDependency(packageName("@types/react-dom"), REACT)
        .addDevDependency(packageName("@tsconfig/vite-react"), REACT)
        .addDevDependency(packageName("@vitejs/plugin-react"), REACT)
        .addDevDependency(packageName("eslint-plugin-react"), REACT)
        .addDevDependency(packageName("jsdom"), COMMON)
        .addDevDependency(packageName("vite"), COMMON)
        .addDependency(packageName("react"), REACT)
        .addDependency(packageName("react-dom"), REACT)
        .addDependency(packageName("piqure"), COMMON)
        .addScript(scriptKey("dev"), scriptCommand("npm-run-all --parallel dev:*"))
        .addScript(scriptKey("dev:vite"), scriptCommand("vite"))
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:tsc"), scriptCommand("tsc -b"))
        .addScript(scriptKey("build:vite"), scriptCommand("vite build --emptyOutDir"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .and()
      .postActions()
        .add(context -> nodeLazyPackagesInstaller.runInstallIn(context.projectFolder(), properties.nodePackageManager()))
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addTemplate("vite.config.ts")
          .and()
        .batch(APP_SOURCE, APP_DESTINATION)
          .addTemplate("index.css")
          .addTemplate("index.tsx")
          .addTemplate("vite-env.d.ts")
          .and()
        .batch(PIQURE_SOURCE, APP_DESTINATION)
          .addTemplate("injections.ts")
        .and()
        .add(WEBAPP_SOURCE.template("index.html"), WEBAPP_DESTINATION.append("index.html"))
        .add(SOURCE.append(TEST_PRIMARY).template("HomePage.spec.tsx"), to(TEST_PRIMARY).append("HomePage.spec.tsx"))
        .add(PRIMARY_APP_SOURCE.template("HomePage.tsx"), PRIMARY_APP_DESTINATION.append("HomePage.tsx"))
        .add(PRIMARY_APP_SOURCE.template("HomePage.css"), PRIMARY_APP_DESTINATION.append("HomePage.css"))
        .batch(WEBAPP_SOURCE.append(ASSETS), WEBAPP_DESTINATION.append(ASSETS))
          .addFile("JHipster-Lite-neon-blue.png")
          .addFile("ReactLogo.png")
          .and()
        .and()
      .apply(patchEslintConfig(properties))
      .apply(patchTsConfig(properties))
      .apply(patchVitestConfig())
      .build();
    // @formatter:on
  }

  private Consumer<SeedModuleBuilder> patchEslintConfig(SeedModuleProperties properties) {
    String reactConfig = """
      \t\tfiles: ['src/main/webapp/**/*.{ts,tsx}', 'src/test/webapp/unit/**/*.{ts,tsx}'],
      \t\textends: [...typescript.configs.recommendedTypeChecked, react],
      \t\tsettings: {
      \t\t\treact: {
      \t\t\t\tversion: 'detect',
      \t\t\t},
      \t\t},\
      """.replace("\t", properties.indentation().spaces());
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("eslint.config.js"))
          .add(lineAfterRegex("from 'typescript-eslint'"), "import react from 'eslint-plugin-react/configs/recommended.js';")
            .add(regex(always(), "\\s+\\.\\.\\.typescript\\.configs\\.recommended.*"), "")
            .add(regex("[ \\t]+files: \\['src/\\*/webapp/\\*\\*\\/\\*\\.ts'],"), reactConfig)
            .add(
              lineAfterRegex("globals: \\{ \\.\\.\\.globals\\.browser },"),
              """
              \t\t\tparserOptions: {
              \t\t\t\tproject: ['./tsconfig.json'],
              \t\t\t},\
              """.replace("\t", properties.indentation().spaces())
            )
            .add(
              regex("[ \\t]+quotes: \\['error', 'single', \\{ avoidEscape: true }],"),
              """
              \t\t\t'@typescript-eslint/no-misused-promises': [
              \t\t\t\t'error',
              \t\t\t\t{
              \t\t\t\t\tchecksVoidReturn: false,
              \t\t\t\t},
              \t\t\t],\
              """.replace("\t", properties.indentation().spaces())
            )
          .and()
        .and()
      .apply(eslintTypescriptRule("'@typescript-eslint/consistent-type-imports': 'error'", properties.indentation()))
      .apply(eslintTypescriptRule("'@typescript-eslint/await-thenable': 'off'", properties.indentation()))
      .apply(eslintTypescriptRule("'@typescript-eslint/no-explicit-any': 'off'", properties.indentation()))
      .apply(eslintTypescriptRule("'react/react-in-jsx-scope': 'off'", properties.indentation()));
    // @formatter:on
  }

  private Consumer<SeedModuleBuilder> patchTsConfig(SeedModuleProperties properties) {
    String pathsReplacement =
      DEFAULT_TSCONFIG_PATH + "," + LINE_BREAK + properties.indentation().times(3) + "\"@assets/*\": [\"src/main/webapp/assets/*\"]";
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("tsconfig.json"))
          .add(text("@tsconfig/recommended/tsconfig.json"), "@tsconfig/vite-react/tsconfig.json")
          .add(text(DEFAULT_TSCONFIG_PATH), pathsReplacement)
          .and()
      .and()
      .apply(tsConfigCompilerOption("composite", false, properties.indentation()))
      .apply(tsConfigCompilerOption("forceConsistentCasingInFileNames", true, properties.indentation()))
      .apply(tsConfigCompilerOption("allowSyntheticDefaultImports", true, properties.indentation()));
    // @formatter:on
  }

  private Consumer<SeedModuleBuilder> patchVitestConfig() {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("vitest.config.ts"))
          .add(lineAfterRegex("from 'vitest/config';"), "import react from '@vitejs/plugin-react';")
          .add(text("plugins: ["), "plugins: [react(), ")
          .add(text("environment: 'node',"), "environment: 'jsdom',")
          .and()
      .and()
      .apply(vitestCoverageExclusion("src/main/webapp/app/index.tsx"))
      .apply(vitestCoverageExclusion("src/main/webapp/app/injections.ts"));
    // @formatter:on
  }
}
