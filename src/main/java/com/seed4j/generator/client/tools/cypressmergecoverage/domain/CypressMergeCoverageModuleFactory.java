package com.seed4j.generator.client.tools.cypressmergecoverage.domain;

import static com.seed4j.module.domain.Seed4JModule.fileStart;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.lineAfterRegex;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.regex;
import static com.seed4j.module.domain.Seed4JModule.scriptCommand;
import static com.seed4j.module.domain.Seed4JModule.scriptKey;
import static com.seed4j.module.domain.Seed4JModule.text;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import java.util.function.Consumer;

public class CypressMergeCoverageModuleFactory {

  private static final Seed4JSource SOURCE = from("client/tools/cypressmergecoverage");
  private static final String CYPRESS_COMPONENT_TESTS = "src/test/webapp/component";

  public Seed4JModule buildCypressMergeCoverage(Seed4JModuleProperties properties) {
    // @formatter:off
    return Seed4JModule.moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("@cypress/code-coverage"), COMMON)
        .addDependency(packageName("cpy-cli"), COMMON)
        .addDependency(packageName("rimraf"), COMMON)
        .addDependency(packageName("vite-plugin-istanbul"), COMMON)
        .addDependency(packageName("nyc"), COMMON)
        .addScript(
          scriptKey("test:coverage:check"),
          scriptCommand("npm-run-all test:coverage:clean test:coverage:copy:* test:coverage:merge test:coverage:report")
        )
        .addScript(scriptKey("test:coverage:clean"), scriptCommand("rimraf target/.nyc_output"))
        .addScript(
          scriptKey("test:coverage:copy:unit"),
          scriptCommand(
            "cpy --flat target/frontend-coverage/unit-tests/coverage-final.json target/.nyc_output/ --rename=unit-coverage-final.json"
          )
        )
        .addScript(
          scriptKey("test:coverage:copy:component"),
          scriptCommand(
            "cpy --flat target/frontend-coverage/components-tests/coverage-final.json target/.nyc_output/ --rename=component-coverage-final.json"
          )
        )
        .addScript(
          scriptKey("test:coverage:merge"),
          scriptCommand("nyc merge target/.nyc_output target/.nyc_output/frontend-combined-coverage.json")
        )
        .addScript(scriptKey("test:coverage:report"), scriptCommand("nyc report --reporter=lcov --reporter=text"))
      .and()
      .apply(patchViteConfig())
      .apply(patchVitestConfig())
      .apply(patchCypressConfig(properties))
      .files()
        .batch(SOURCE, to("."))
          .addTemplate(".nycrc.json")
        .and()
        .batch(SOURCE.append(CYPRESS_COMPONENT_TESTS), to(CYPRESS_COMPONENT_TESTS))
          .addTemplate(".nycrc.json")
        .and()
        .batch(SOURCE.append(CYPRESS_COMPONENT_TESTS).append("support"), to(CYPRESS_COMPONENT_TESTS).append("/support"))
          .addFile("component-tests.ts")
        .and()
      .and()
      .build();
    // @formatter:on
  }

  private Consumer<Seed4JModule.Seed4JModuleBuilder> patchCypressConfig(Seed4JModuleProperties properties) {
    return moduleBuilder ->
      moduleBuilder
        .mandatoryReplacements()
        .in(path(CYPRESS_COMPONENT_TESTS + "/cypress-config.ts"))
        .add(fileStart(), "import registerCodeCoverageTasks from '@cypress/code-coverage/task';\n")
        .add(
          lineAfterRegex("e2e:"),
          properties.indentation().times(2)
            + """
            setupNodeEvents(on, config) {
                  registerCodeCoverageTasks(on, config);
                  return config;
                },"""
        )
        .add(text("supportFile: false"), "supportFile: 'src/test/webapp/component/support/component-tests.ts'");
  }

  private Consumer<Seed4JModule.Seed4JModuleBuilder> patchVitestConfig() {
    return moduleBuilder ->
      moduleBuilder
        .mandatoryReplacements()
        .in(path("vitest.config.ts"))
        .add(regex("reportsDirectory: '(.*?)/test-results/'"), "reportsDirectory: 'target/frontend-coverage/unit-tests/'");
  }

  private Consumer<Seed4JModule.Seed4JModuleBuilder> patchViteConfig() {
    return moduleBuilder ->
      moduleBuilder
        .mandatoryReplacements()
        .in(path("vite.config.ts"))
        .add(lineAfterRegex("from 'vite'"), "import istanbul from 'vite-plugin-istanbul';")
        .add(text("plugins: ["), "plugins: [istanbul(), ");
  }
}
