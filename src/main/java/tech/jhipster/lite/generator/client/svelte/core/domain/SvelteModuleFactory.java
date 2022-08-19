package tech.jhipster.lite.generator.client.svelte.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.lineBeforeText;
import static tech.jhipster.lite.module.domain.JHipsterModule.packageName;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptCommand;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.SVELTE;

import tech.jhipster.lite.generator.client.common.domain.ClientsModulesFactory;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SvelteModuleFactory {

  private static final JHipsterSource SOURCE = from("client/svelte");
  private static final JHipsterSource INIT = from("init");

  private static final String CACHE_NEEDLE = "  \"cacheDirectories\":";

  public JHipsterModule buildSvelteModule(JHipsterModuleProperties properties) {
    return ClientsModulesFactory
      .clientModuleBuilder(properties)
      .packageJson()
      .addDependency(packageName("svelte-navigator"), SVELTE)
      .addDevDependency(packageName("@babel/preset-env"), SVELTE)
      .addDevDependency(packageName("@sveltejs/adapter-static"), SVELTE)
      .addDevDependency(packageName("@sveltejs/kit"), SVELTE)
      .addDevDependency(packageName("@testing-library/svelte"), SVELTE)
      .addDevDependency(packageName("@testing-library/jest-dom"), SVELTE)
      .addDevDependency(packageName("@types/jest"), SVELTE)
      .addDevDependency(packageName("@typescript-eslint/eslint-plugin"), SVELTE)
      .addDevDependency(packageName("@typescript-eslint/parser"), SVELTE)
      .addDevDependency(packageName("babel-jest"), SVELTE)
      .addDevDependency(packageName("babel-plugin-transform-vite-meta-env"), SVELTE)
      .addDevDependency(packageName("eslint"), SVELTE)
      .addDevDependency(packageName("eslint-config-prettier"), SVELTE)
      .addDevDependency(packageName("eslint-plugin-svelte3"), SVELTE)
      .addDevDependency(packageName("jest"), SVELTE)
      .addDevDependency(packageName("jest-environment-jsdom"), SVELTE)
      .addDevDependency(packageName("jest-transform-stub"), SVELTE)
      .addDevDependency(packageName("jest-sonar-reporter"), SVELTE)
      .addDevDependency(packageName("prettier"), SVELTE)
      .addDevDependency(packageName("prettier-plugin-svelte"), SVELTE)
      .addDevDependency(packageName("svelte"), SVELTE)
      .addDevDependency(packageName("svelte-check"), SVELTE)
      .addDevDependency(packageName("svelte-preprocess"), SVELTE)
      .addDevDependency(packageName("svelte-jester"), SVELTE)
      .addDevDependency(packageName("tslib"), SVELTE)
      .addDevDependency(packageName("ts-jest"), SVELTE)
      .addDevDependency(packageName("typescript"), SVELTE)
      .addDevDependency(packageName("vite"), SVELTE)
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
      .addScript(scriptKey("test"), scriptCommand("jest"))
      .and()
      .optionalReplacements()
      .in("package.json")
      .add(lineBeforeText(CACHE_NEEDLE), type(properties.indentation()))
      .and()
      .and()
      .files()
      .add(SOURCE.file(".eslintrc.cjs"), to(".eslintrc.cjs"))
      .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
      .add(SOURCE.file("svelte.config.js"), to("svelte.config.js"))
      .add(SOURCE.file("jest.config.cjs"), to("jest.config.cjs"))
      .add(SOURCE.file("vite.config.js"), to("vite.config.js"))
      .batch(SOURCE.file("src/main/webapp"), to("src/main/webapp"))
      .addTemplate("app.html")
      .addTemplate("app.d.ts")
      .addTemplate("jest-setup.ts")
      .and()
      .and()
      .files()
      .add(SOURCE.append("src/main/webapp/routes").template("index.svelte"), to("src/main/webapp/routes/index.svelte"))
      .add(
        SOURCE.append("src/test/spec/common/primary/app").template("App.spec.ts"),
        to("src/test/javascript/spec/common/primary/app/App.spec.ts")
      )
      .and()
      .files()
      .add(
        SOURCE.append("src/main/webapp/app/common/primary/app").template("StyledApp.svelte"),
        to("src/main/webapp/app/common/primary/app/App.svelte")
      )
      .batch(SOURCE.file("src/main/webapp/assets"), to("src/main/webapp/assets"))
      .addFile("JHipster-Lite-neon-orange.png")
      .addFile("svelte-logo.png")
      .and()
      .and()
      .files()
      .add(INIT.file(".lintstagedrc.js"), to(".lintstagedrc.cjs"))
      .and()
      .build();
  }

  private static String type(Indentation indentation) {
    return indentation.spaces() + "\"type\": \"module\"";
  }
}
