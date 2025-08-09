package com.seed4j.generator.server.springboot.cucumber.domain;

import static com.seed4j.generator.server.springboot.cucumbercommon.domain.CucumbersModules.cucumberModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CucumberModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/cucumber");

  public JHipsterModule buildInitializationModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    SeedDestination destination = toSrcTestJava().append(properties.packagePath()).append("cucumber");

    // @formatter:off
    JHipsterModuleBuilder builder = cucumberModuleBuilder(properties)
    .context()
      .put("baseName", baseName)
      .and()
    .documentation(documentationTitle("Cucumber"), SOURCE.template("cucumber.md"))
    .files()
      .batch(SOURCE, destination)
        .addTemplate("CucumberConfiguration.java")
        .addTemplate("CucumberTest.java")
        .and()
      .batch(SOURCE.append("rest"), destination.append("rest"))
        .addTemplate("AsyncElementAsserter.java")
        .addTemplate("AsyncHeaderAsserter.java")
        .addTemplate("AsyncResponseAsserter.java")
        .addTemplate("Awaiter.java")
        .addTemplate("CucumberRestAssertions.java")
        .addTemplate("CucumberRestTemplate.java")
        .addTemplate("CucumberJson.java")
        .addTemplate("CucumberRestTestContext.java")
        .addTemplate("CucumberRestTestContextUnitTest.java")
        .addTemplate("ElementAsserter.java")
        .addTemplate("ElementAssertions.java")
        .addTemplate("HeaderAsserter.java")
        .addTemplate("HeaderAssertions.java")
        .addTemplate("ResponseAsserter.java")
        .addTemplate("SyncElementAsserter.java")
        .addTemplate("SyncHeaderAsserter.java")
        .addTemplate("SyncResponseAsserter.java")
        .and()
      .add(SOURCE.file("gitkeep"), to("src/test/features/.gitkeep"))
      .and();
    // @formatter:on

    return builder.build();
  }

  public JHipsterModule buildJpaResetModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .files()
      .add(
        SOURCE.template("CucumberJpaReset.java"),
        toSrcTestJava().append(properties.packagePath()).append("cucumber").append("CucumberJpaReset.java")
      )
      .and()
      .build();
  }
}
