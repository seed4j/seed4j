package com.seed4j.generator.server.springboot.cucumber.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CucumberModuleFactoryTest {

  private static final CucumberModuleFactory factory = new CucumberModuleFactory();

  @Test
  void shouldBuildInitialModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildInitializationModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles("src/test/java/com/seed4j/growth/cucumber", "CucumberConfiguration.java", "CucumberTest.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/cucumber/rest",
        "AsyncElementAsserter.java",
        "AsyncHeaderAsserter.java",
        "AsyncResponseAsserter.java",
        "Awaiter.java",
        "CucumberRestAssertions.java",
        "CucumberRestClient.java",
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
      .containing("<artifactId>spring-boot-resttestclient</artifactId>")
      .containing("<artifactId>junit-platform-suite</artifactId>")
      .containing("<version>${cucumber.version}</version>")
      .and()
      .doNotHaveFiles("src/test/java/com/seed4j/growth/cucumber/CucumberJpaReset.java");
  }

  @Test
  void shouldBuildJpaResetModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildJpaResetModule(properties);

    assertThatModuleWithFiles(module, pomFile()).hasFiles("src/test/java/com/seed4j/growth/cucumber/CucumberJpaReset.java");
  }
}
