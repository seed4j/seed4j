package com.seed4j.generator.client.tools.playwright.domain;

import static com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.scriptCommand;
import static com.seed4j.module.domain.Seed4JModule.scriptKey;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PlaywrightModuleFactory {

  private static final Seed4JSource SOURCE = from("client/common/playwright");

  private static final Seed4JDestination WEBAPP_COMPONENT_TESTS = to("src/test/webapp/component/");
  private static final Seed4JDestination WEBAPP_E2E_TESTS = to("src/test/webapp/e2e/");
  private static final String PLAYWRIGHT_TESTS = "common/primary/app";

  public Seed4JModule buildComponentTestsModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return commonPlaywrightModuleBuilder(properties, WEBAPP_COMPONENT_TESTS)
      .packageJson()
        .addDevDependency(packageName("start-server-and-test"), COMMON)
        .addScript(scriptKey("test:component"), scriptCommand("start-server-and-test dev http://localhost:9000 'playwright test --ui --config src/test/webapp/component/playwright.config.ts'"))
        .addScript(scriptKey("test:component:headless"), scriptCommand("start-server-and-test dev http://localhost:9000 'playwright test --config src/test/webapp/component/playwright.config.ts'"))
        .and()
      .context()
        .put("reportSubDirectory", "component-tests")
        .put("resultsSubDirectory", "component-tests")
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildE2ETestsModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return commonPlaywrightModuleBuilder(properties, WEBAPP_E2E_TESTS)
      .packageJson()
        .addScript(scriptKey("e2e"), scriptCommand("playwright test --ui --config src/test/webapp/e2e/playwright.config.ts"))
        .addScript(scriptKey("e2e:headless"), scriptCommand("playwright test --config src/test/webapp/e2e/playwright.config.ts"))
        .and()
      .context()
        .put("reportSubDirectory", "e2e-tests")
        .put("resultsSubDirectory", "e2e-tests")
        .and()
      .build();
    // @formatter:on
  }

  private static Seed4JModuleBuilder commonPlaywrightModuleBuilder(Seed4JModuleProperties properties, Seed4JDestination destinationFolder) {
    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("@playwright/test"), COMMON)
        .and()
      .files()
        .add(SOURCE.template("playwright.config.ts"), destinationFolder.append("playwright.config.ts"))
        .batch(SOURCE.append(PLAYWRIGHT_TESTS), destinationFolder.append(PLAYWRIGHT_TESTS))
          .addTemplate("Home.spec.ts")
          .addFile("Home-Page.ts")
          .and()
      .and();
    // @formatter:on
  }
}
