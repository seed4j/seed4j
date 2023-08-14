package tech.jhipster.lite.generator.client.svelte.core.domain;

import static tech.jhipster.lite.generator.client.common.domain.ClientsModulesFactory.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SvelteModuleFactory {

  private static final JHipsterSource SOURCE = from("client/svelte");

  private static final String CACHE_NEEDLE = "  \"cacheDirectories\":";

  private static final JHipsterSource PRIMARY_MAIN_SOURCE = SOURCE.append("src/main/webapp/app/common/primary/app");
  private static final JHipsterDestination PRIMARY_MAIN_DESTINATION = to("src/main/webapp/app/common/primary/app");
  private static final JHipsterSource COMMON_ESLINT = from("client/common/eslint");

  private static final JHipsterSource PRIMARY_TEST_SOURCE = SOURCE.append("src/test/spec/common/primary/app");
  private static final JHipsterDestination PRIMARY_TEST_DESTINATION = to("src/test/javascript/spec/common/primary/app");

  public JHipsterModule buildSvelteModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return clientModuleBuilder(properties)
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
        .addDevDependency(packageName("eslint-config-prettier"), SVELTE)
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
          scriptCommand("prettier --ignore-path .gitignore --check --plugin-search-dir=. . && eslint --ignore-path .gitignore .")
        )
        .addScript(scriptKey("format"), scriptCommand("prettier --ignore-path .gitignore --write --plugin-search-dir=. ."))
        .addScript(scriptKey("test"), scriptCommand("vitest run --coverage"))
        .and()
      .optionalReplacements()
        .in(path("package.json"))
          .add(lineBeforeText(CACHE_NEEDLE), type(properties.indentation()))
          .and()
        .and()
      .files()
        .add(SOURCE.file(".eslintrc.cjs"), to(".eslintrc.cjs"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("svelte.config.js"), to("svelte.config.js"))
        .add(SOURCE.file("vite.config.js"), to("vite.config.js"))
        .add(SOURCE.file("vitest.config.ts"), to("vitest.config.ts"))
        .add(SOURCE.append("src/main/webapp/routes").template("+page.svelte"), to("src/main/webapp/routes/+page.svelte"))
        .add(PRIMARY_MAIN_SOURCE.template("App.svelte"), PRIMARY_MAIN_DESTINATION.append("App.svelte"))
        .add(PRIMARY_TEST_SOURCE.template("App.spec.ts"), PRIMARY_TEST_DESTINATION.append("App.spec.ts"))
        .add(COMMON_ESLINT.file(".eslintignore"), to(".eslintignore"))
        .move(path(".lintstagedrc.js"), to(".lintstagedrc.cjs"))
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
