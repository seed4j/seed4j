package com.seed4j.generator.server.springboot.cucumber.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CucumberModuleFactoryTest {

  private static final CucumberModuleFactory factory = new CucumberModuleFactory();

  @Test
  void shouldBuildInitialModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildInitializationModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles("src/test/java/com/seed4j/growth/cucumber", "CucumberConfiguration.java", "CucumberTest.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/cucumber/rest",
        "AsyncElementAsserter.java",
        "AsyncHeaderAsserter.java",
        "AsyncResponseAsserter.java",
        "Awaiter.java",
        "CucumberRestAssertions.java",
        "CucumberRestTemplate.java",
        "CucumberJson.java",
        "CucumberRestTestContext.java",
        "CucumberRestTestContextUnitTest.java",
        "ElementAsserter.java",
        "ElementAssertions.java",
        "HeaderAsserter.java",
        "HeaderAssertions.java",
        "ResponseAsserter.java",
        "SyncElementAsserter.java",
        "SyncHeaderAsserter.java",
        "SyncResponseAsserter.java"
      )
      .hasFiles("documentation/cucumber.md")
      .hasFiles("src/test/features/.gitkeep")
      .hasFile("pom.xml")
      .containing("<artifactId>cucumber-junit-platform-engine</artifactId>")
      .containing("<artifactId>cucumber-java</artifactId>")
      .containing("<artifactId>cucumber-spring</artifactId>")
      .containing("<artifactId>junit-platform-suite</artifactId>")
      .containing("<version>${cucumber.version}</version>")
      .and()
      .doNotHaveFiles("src/test/java/com/seed4j/growth/cucumber/CucumberJpaReset.java");
  }

  @Test
  void shouldBuildJpaResetModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildJpaResetModule(properties);

    assertThatModuleWithFiles(module, pomFile()).hasFiles("src/test/java/com/seed4j/growth/cucumber/CucumberJpaReset.java");
  }
}
