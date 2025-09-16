package com.seed4j.generator.client.tools.playwright.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class PlaywrightModuleFactoryTest {

  private static final PlaywrightModuleFactory factory = new PlaywrightModuleFactory();

  @Test
  void shouldBuildComponentTestsModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildComponentTestsModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
      .containing(nodeDependency("start-server-and-test"))
      .containing(nodeDependency("@playwright/test"))
      .containing(
        nodeScript(
          "test:component",
          "start-server-and-test dev http://localhost:9000 'playwright test --ui --config src/test/webapp/component/playwright.config.ts'"
        )
      )
      .containing(
        nodeScript(
          "test:component:headless",
          "start-server-and-test dev http://localhost:9000 'playwright test --config src/test/webapp/component/playwright.config.ts'"
        )
      )
      .and()
      .hasFile("src/test/webapp/component/playwright.config.ts")
      .containing("baseURL: process.env.URL || 'http://localhost:9000',")
      .containing("outputFolder: '../../../../target/playwright-report/component-tests'")
      .containing("outputDir: '../../../../target/playwright-results/component-tests'")
      .and()
      .hasPrefixedFiles("src/test/webapp/component/common/primary/app", "Home.spec.ts", "Home-Page.ts");
  }

  @Test
  void shouldBuildE2ETestsModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildE2ETestsModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
      .containing(nodeDependency("@playwright/test"))
      .containing(nodeScript("e2e", "playwright test --ui --config src/test/webapp/e2e/playwright.config.ts"))
      .containing(nodeScript("e2e:headless", "playwright test --config src/test/webapp/e2e/playwright.config.ts"))
      .and()
      .hasFile("src/test/webapp/e2e/playwright.config.ts")
      .containing("baseURL: process.env.URL || 'http://localhost:9000',")
      .containing("outputFolder: '../../../../target/playwright-report/e2e-tests'")
      .containing("outputDir: '../../../../target/playwright-results/e2e-tests'")
      .and()
      .hasPrefixedFiles("src/test/webapp/e2e/common/primary/app", "Home.spec.ts", "Home-Page.ts");
  }
}
