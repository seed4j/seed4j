package tech.jhipster.lite.generator.client.svelte.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.COMMON;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.SVELTE;

import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SvelteModuleFactory {

  private static final JHipsterSource SOURCE = from("client/svelte");

  private static final String ENGINES_NEEDLE = "  \"engines\":";

  private static final JHipsterSource PRIMARY_MAIN_SOURCE = SOURCE.append("src/main/webapp/app/common/primary/app");
  private static final JHipsterDestination PRIMARY_MAIN_DESTINATION = to("src/main/webapp/app/common/primary/app");
  private static final JHipsterSource CLIENT_COMMON = from("client/common");

  private static final JHipsterSource PRIMARY_TEST_SOURCE = SOURCE.append("src/test/unit/common/primary/app");
  private static final JHipsterDestination PRIMARY_TEST_DESTINATION = to("src/test/webapp/unit/common/primary/app");

  public JHipsterModule buildSvelteModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .preCommitActions(stagedFilesFilter("{src/**/,}*.ts"), preCommitCommands("eslint --fix", "prettier --write"))
      .gitIgnore()
        .comment("Svelte")
        .pattern(".svelte-kit/")
        .and()
      .packageJson()
        .addDependency(packageName("svelte-navigator"), SVELTE)
        .addDevDependency(packageName("@babel/preset-env"), SVELTE)
        .addDevDependency(packageName("@sveltejs/adapter-static"), SVELTE)
        .addDevDependency(packageName("@sveltejs/kit"), SVELTE)
        .addDevDependency(packageName("@testing-library/svelte"), SVELTE)
        .addDevDependency(packageName("@typescript-eslint/eslint-plugin"), SVELTE)
        .addDevDependency(packageName("@typescript-eslint/parser"), SVELTE)
        .addDevDependency(packageName("@vitest/coverage-istanbul"), SVELTE)
        .addDevDependency(packageName("babel-plugin-transform-vite-meta-env"), SVELTE)
        .addDevDependency(packageName("eslint"), SVELTE)
        .addDevDependency(packageName("eslint-config-prettier"), COMMON)
        .addDevDependency(packageName("eslint-plugin-svelte3"), SVELTE)
        .addDevDependency(packageName("jsdom"), SVELTE)
        .addDevDependency(packageName("prettier"), SVELTE)
        .addDevDependency(packageName("prettier-plugin-svelte"), SVELTE)
        .addDevDependency(packageName("svelte"), SVELTE)
        .addDevDependency(packageName("svelte-check"), SVELTE)
        .addDevDependency(packageName("svelte-preprocess"), SVELTE)
        .addDevDependency(packageName("tslib"), SVELTE)
        .addDevDependency(packageName("typescript"), SVELTE)
        .addDevDependency(packageName("vite"), SVELTE)
        .addDevDependency(packageName("vitest"), SVELTE)
        .addDevDependency(packageName("vitest-sonar-reporter"), SVELTE)
        .addScript(scriptKey("dev"), scriptCommand("vite dev --port 9000"))
        .addScript(scriptKey("start"), scriptCommand("vite dev --port 9000"))
        .addScript(scriptKey("build"), scriptCommand("vite build"))
        .addScript(scriptKey("package"), scriptCommand("vite package"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("check"), scriptCommand("svelte-check --tsconfig ./tsconfig.json"))
        .addScript(scriptKey("check:watch"), scriptCommand("svelte-check --tsconfig ./tsconfig.json --watch"))
        .addScript(
          scriptKey("lint"),
          scriptCommand("prettier --ignore-path .gitignore --check && eslint --ignore-path .gitignore .")
        )
        .addScript(scriptKey("format"), scriptCommand("prettier --ignore-path .gitignore --write"))
        .addScript(scriptKey("test"), scriptCommand("npm run test:watch"))
        .addScript(scriptKey("test:coverage"), scriptCommand("vitest run --coverage"))
        .addScript(scriptKey("test:watch"), scriptCommand("vitest --"))
        .and()
      .optionalReplacements()
        .in(path("package.json"))
          .add(lineBeforeText(ENGINES_NEEDLE), type(properties.indentation()))
          .and()
        .and()
      .files()
        .add(SOURCE.file(".eslintrc.cjs"), to(".eslintrc.cjs"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("vite.config.js"), to("vite.config.js"))
        .add(SOURCE.append("src/main/webapp/routes").template("+page.svelte"), to("src/main/webapp/routes/+page.svelte"))
        .add(PRIMARY_MAIN_SOURCE.template("App.svelte"), PRIMARY_MAIN_DESTINATION.append("App.svelte"))
        .add(PRIMARY_TEST_SOURCE.template("App.spec.ts"), PRIMARY_TEST_DESTINATION.append("App.spec.ts"))
        .batch(CLIENT_COMMON, to("."))
          .addFile(".eslintignore")
          .addFile(".npmrc")
          .and()
        .batch(SOURCE, to("."))
          .addTemplate("svelte.config.js")
          .addTemplate("vitest.config.ts")
          .and()
        .batch(SOURCE.file("src/main/webapp"), to("src/main/webapp"))
          .addTemplate("app.html")
          .addTemplate("app.d.ts")
          .and()
        .batch(SOURCE.file("src/main/webapp/assets"), to("src/main/webapp/assets"))
          .addFile("JHipster-Lite-neon-orange.png")
          .addFile("svelte-logo.png")
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private static String type(Indentation indentation) {
    return indentation.spaces() + "\"type\": \"module\",";
  }
}
