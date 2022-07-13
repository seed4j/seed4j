package tech.jhipster.lite.generator.server.springboot.cucumber.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class CucumberModuleFactoryTest {

  private static final CucumberModuleFactory factory = new CucumberModuleFactory();

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/cucumber",
        "AsyncElementAsserter.java",
        "AsyncHeaderAsserter.java",
        "AsyncResponseAsserter.java",
        "Awaiter.java",
        "CucumberAssertions.java",
        "CucumberConfiguration.java",
        "CucumberJson.java",
        "CucumberTest.java",
        "CucumberTestContext.java",
        "CucumberTestContextUnitTest.java",
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
      .containing("<artifactId>cucumber-junit</artifactId>")
      .containing("<artifactId>cucumber-java</artifactId>")
      .containing("<artifactId>cucumber-spring</artifactId>")
      .containing("<artifactId>junit-vintage-engine</artifactId>")
      .containing("<artifactId>testng</artifactId>")
      .containing("<artifactId>awaitility</artifactId>")
      .containing("<version>${cucumber.version}</version>")
      .and()
      .doNotHaveFiles("src/test/java/com/jhipster/test/cucumber/CucumberJpaReset.java");
  }

  @Test
  void shouldAddDataResetWithSelectedOption() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .put("jpaReset", true)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile()).hasFiles("src/test/java/com/jhipster/test/cucumber/CucumberJpaReset.java");
  }
}
