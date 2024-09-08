package tech.jhipster.lite.generator.client.react.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.COMMON;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.REACT;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class ReactCoreModulesFactory {

  private static final JHipsterSource SOURCE = from("client/react/core");
  private static final JHipsterSource SOURCE_COMMON = from("client/common");

  private static final JHipsterSource WEBAPP_SOURCE = SOURCE.append("src/main/webapp");
  private static final JHipsterDestination WEBAPP_DESTINATION = to("src/main/webapp");

  private static final JHipsterSource APP_SOURCE = WEBAPP_SOURCE.append("app");
  private static final JHipsterDestination APP_DESTINATION = WEBAPP_DESTINATION.append("app");

  private static final String PRIMARY_APP = "common/primary/app";
  private static final String ASSETS = "assets";

  private static final JHipsterSource PRIMARY_APP_SOURCE = APP_SOURCE.append(PRIMARY_APP);
  private static final JHipsterDestination PRIMARY_APP_DESTINATION = APP_DESTINATION.append(PRIMARY_APP);

  private static final String TEST_PRIMARY = "src/test/webapp/unit/common/primary/app";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .optionalReplacements()
        .in(path(".lintstagedrc.cjs"))
          .add(lineBeforeRegex("\\['prettier --write'\\]"), "  '{src/**/,}*.{ts,tsx}': ['eslint --fix', 'prettier --write'],")
          .and()
        .and()
      .packageJson()
        .addDevDependency(packageName("@testing-library/dom"), REACT)
        .addDevDependency(packageName("@testing-library/react"), REACT)
        .addDevDependency(packageName("@types/node"), COMMON)
        .addDevDependency(packageName("@types/react"), REACT)
        .addDevDependency(packageName("@types/react-dom"), REACT)
        .addDevDependency(packageName("@tsconfig/vite-react"), REACT)
        .addDevDependency(packageName("@typescript-eslint/eslint-plugin"), COMMON)
        .addDevDependency(packageName("@vitejs/plugin-react"), REACT)
        .addDevDependency(packageName("@vitest/coverage-istanbul"), COMMON)
        .addDevDependency(packageName("typescript-eslint"), COMMON)
        .addDevDependency(packageName("globals"), COMMON)
        .addDevDependency(packageName("eslint"), COMMON)
        .addDevDependency(packageName("eslint-plugin-react"), REACT)
        .addDevDependency(packageName("jsdom"), COMMON)
        .addDevDependency(packageName("typescript"), COMMON)
        .addDevDependency(packageName("ts-node"), REACT)
        .addDevDependency(packageName("vite"), COMMON)
        .addDevDependency(packageName("vite-tsconfig-paths"), COMMON)
        .addDevDependency(packageName("vitest"), COMMON)
        .addDevDependency(packageName("vitest-sonar-reporter"), COMMON)
        .addDependency(packageName("react"), REACT)
        .addDependency(packageName("react-dom"), REACT)
        .addDependency(packageName("npm-run-all2"), COMMON)
        .addScript(scriptKey("dev"), scriptCommand("npm-run-all dev:*"))
        .addScript(scriptKey("dev:vite"), scriptCommand("vite"))
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:vite"), scriptCommand("tsc && vite build --emptyOutDir"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .addScript(scriptKey("lint"), scriptCommand("eslint ."))
        .addScript(scriptKey("watch"), scriptCommand("npm-run-all --parallel watch:*"))
        .addScript(scriptKey("watch:tsc"), scriptCommand("tsc --noEmit --watch"))
        .addScript(scriptKey("test"), scriptCommand("npm run watch:test"))
        .addScript(scriptKey("test:coverage"), scriptCommand("vitest run --coverage"))
        .addScript(scriptKey("watch:test"), scriptCommand("vitest --"))
      .and()
      .files()
        .batch(SOURCE, to("."))
          .addFile("tsconfig.json")
          .addTemplate("vite.config.ts")
          .addTemplate("vitest.config.ts")
          .addTemplate("eslint.config.js")
          .and()
        .batch(SOURCE_COMMON, to("."))
          .addFile(".npmrc")
          .and()
        .batch(APP_SOURCE, APP_DESTINATION)
          .addTemplate("index.css")
          .addTemplate("index.tsx")
          .addTemplate("vite-env.d.ts")
          .and()
        .add(WEBAPP_SOURCE.template("index.html"), WEBAPP_DESTINATION.append("index.html"))
        .add(SOURCE.append(TEST_PRIMARY).template("App.spec.tsx"), to(TEST_PRIMARY).append("App.spec.tsx"))
        .add(PRIMARY_APP_SOURCE.template("App.tsx"), PRIMARY_APP_DESTINATION.append("App.tsx"))
        .add(PRIMARY_APP_SOURCE.template("App.css"), PRIMARY_APP_DESTINATION.append("App.css"))
        .batch(WEBAPP_SOURCE.append(ASSETS), WEBAPP_DESTINATION.append(ASSETS))
          .addFile("JHipster-Lite-neon-blue.png")
          .addFile("ReactLogo.png")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
