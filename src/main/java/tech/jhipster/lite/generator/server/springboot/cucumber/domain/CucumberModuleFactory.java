package tech.jhipster.lite.generator.server.springboot.cucumber.domain;

import static tech.jhipster.lite.generator.server.springboot.cucumbercommon.domain.CucumbersModules.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class CucumberModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/cucumber");

  public JHipsterModule buildInitializationModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    JHipsterDestination destination = toSrcTestJava().append(properties.packagePath()).append("cucumber");

    //@formatter:off
    JHipsterModuleBuilder builder = cucumberModuleBuilder(properties)
    .context()
      .put("baseName", baseName)
      .and()
    .documentation(documentationTitle("Cucumber"), SOURCE.template("cucumber.md"))
    .files()
      .batch(SOURCE, destination)
        .addTemplate("AsyncElementAsserter.java")
        .addTemplate("AsyncHeaderAsserter.java")
        .addTemplate("AsyncResponseAsserter.java")
        .addTemplate("Awaiter.java")
        .addTemplate("CucumberAssertions.java")
        .addTemplate("CucumberRestTemplate.java")
        .addTemplate("CucumberConfiguration.java")
        .addTemplate("CucumberJson.java")
        .addTemplate("CucumberTest.java")
        .addTemplate("CucumberTestContext.java")
        .addTemplate("CucumberTestContextUnitTest.java")
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
      .and()
    .javaDependencies()
      .addDependency(awaitilityDependency())
      .and();
    //@formatter:on

    return builder.build();
  }

  private JavaDependency awaitilityDependency() {
    return javaDependency().groupId("org.awaitility").artifactId("awaitility").scope(JavaDependencyScope.TEST).build();
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
