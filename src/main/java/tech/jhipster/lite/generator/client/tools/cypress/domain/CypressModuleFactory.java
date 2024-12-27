package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.packageName;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptCommand;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.COMMON;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class CypressModuleFactory {

  private static final JHipsterSource SOURCE = from("client/common/cypress");

  private static final JHipsterDestination CYPRESS_COMPONENT_TESTS = to("src/test/webapp/component");
  private static final JHipsterDestination CYPRESS_E2E_TESTS = to("src/test/webapp/e2e");

  private static final String HOME = "home";
  private static final String UTILS = "utils";

  public JHipsterModule buildComponentTestsModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return commonCypressModuleBuilder(properties, CYPRESS_COMPONENT_TESTS)
      .packageJson()
        .addDevDependency(packageName("start-server-and-test"), COMMON)
        .addScript(scriptKey("test:component"), scriptCommand("start-server-and-test start http://localhost:9000 'cypress open --e2e --config-file src/test/webapp/component/cypress-config.ts'"))
        .addScript(
          scriptKey("test:component:headless"),
          scriptCommand("start-server-and-test tikui:serve-build http://localhost:9005 start http://localhost:9000 'cypress run --headless --config-file src/test/webapp/component/cypress-config.ts'")
        )
        .and()
      .context()
        .put("cypressTestDirectory", "component")
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildE2ETestsModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return commonCypressModuleBuilder(properties, CYPRESS_E2E_TESTS)
      .packageJson()
        .addScript(scriptKey("e2e"), scriptCommand("cypress open --e2e --config-file src/test/webapp/e2e/cypress-config.ts"))
        .addScript(scriptKey("e2e:headless"), scriptCommand("cypress run --headless --config-file src/test/webapp/e2e/cypress-config.ts"))
        .and()
      .context()
        .put("cypressTestDirectory", "e2e")
        .and()
      .build();
    //@formatter:on
  }

  private static JHipsterModuleBuilder commonCypressModuleBuilder(
    JHipsterModuleProperties properties,
    JHipsterDestination destinationFolder
  ) {
    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("cypress"), COMMON)
        .addDevDependency(packageName("eslint-plugin-cypress"), COMMON)
        .and()
      .files()
        .batch(SOURCE, destinationFolder)
          .addTemplate("cypress-config.ts")
          .addFile("tsconfig.json")
          .and()
        .batch(SOURCE.append(HOME), destinationFolder.append(HOME))
          .addTemplate("Home.spec.ts")
          .and()
        .batch(SOURCE.append(UTILS), destinationFolder.append(UTILS))
          .addFile("Interceptor.ts")
          .addFile("DataSelector.ts")
          .and()
        .and()
      ;
    //@formatter:on
  }
}
