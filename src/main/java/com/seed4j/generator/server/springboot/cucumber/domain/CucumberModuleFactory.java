package com.seed4j.generator.server.springboot.cucumber.domain;

import static com.seed4j.generator.server.springboot.cucumbercommon.domain.CucumbersModules.cucumberModuleBuilder;
import static com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CucumberModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/cucumber");

  public Seed4JModule buildInitializationModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    Seed4JDestination destination = toSrcTestJava().append(properties.packagePath()).append("cucumber");

    // @formatter:off
    Seed4JModuleBuilder builder = cucumberModuleBuilder(properties)
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
        .addTemplate("CucumberRestClient.java")
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

  public Seed4JModule buildJpaResetModule(Seed4JModuleProperties properties) {
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
