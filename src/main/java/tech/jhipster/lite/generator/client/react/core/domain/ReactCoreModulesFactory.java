package tech.jhipster.lite.generator.client.react.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.generator.client.common.domain.ClientsModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class ReactCoreModulesFactory {

  private static final JHipsterSource SOURCE = from("client/react");

  private static final JHipsterSource WEBAPP_SOURCE = SOURCE.append("src/main/webapp");
  private static final JHipsterDestination WEBAPP_DESTINATION = to("src/main/webapp");

  private static final JHipsterSource APP_SOURCE = WEBAPP_SOURCE.append("app");
  private static final JHipsterDestination APP_DESTINATION = WEBAPP_DESTINATION.append("app");

  private static final String PRIMARY_APP = "common/primary/app";
  private static final String ASSETS = "assets";

  private static final JHipsterSource PRIMARY_APP_SOURCE = APP_SOURCE.append(PRIMARY_APP);
  private static final JHipsterDestination PRIMARY_APP_DESTINATION = APP_DESTINATION.append(PRIMARY_APP);

  private static final String TEST_PRIMARY = "src/test/javascript/spec/common/primary/app";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return ClientsModulesFactory.clientModuleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("@testing-library/react"), REACT)
        .addDevDependency(packageName("@testing-library/user-event"), REACT)
        .addDevDependency(packageName("@types/node"), REACT)
        .addDevDependency(packageName("@types/react"), REACT)
        .addDevDependency(packageName("@types/react-dom"), REACT)
        .addDevDependency(packageName("@types/ws"), REACT)
        .addDevDependency(packageName("@vitejs/plugin-react"), REACT)
        .addDevDependency(packageName("@vitest/coverage-istanbul"), REACT)
        .addDevDependency(packageName("jsdom"), REACT)
        .addDevDependency(packageName("typescript"), REACT)
        .addDevDependency(packageName("ts-node"), REACT)
        .addDevDependency(packageName("vite"), REACT)
        .addDevDependency(packageName("vitest"), REACT)
        .addDevDependency(packageName("vitest-sonar-reporter"), REACT)
        .addDependency(packageName("react"), REACT)
        .addDependency(packageName("react-dom"), REACT)
        .addScript(scriptKey("dev"), scriptCommand("vite"))
        .addScript(scriptKey("build"), scriptCommand("tsc && vite build --emptyOutDir"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .addScript(scriptKey("test"), scriptCommand("vitest run --coverage"))
        .addScript(scriptKey("test:watch"), scriptCommand("vitest --"))
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addFile("tsconfig.json")
          .addFile("vite.config.ts")
          .addFile("vitest.config.ts")
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
