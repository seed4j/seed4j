package tech.jhipster.lite.generator.server.springboot.customjhlite.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class CustomJHLiteModuleFactoryTest {

  private static final CustomJHLiteModuleFactory factory = new CustomJHLiteModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    //@formatter:off
    assertThatModuleWithFiles(module, pomFile(), mainAppFile())
      .hasFile("pom.xml")
        .containing("<artifactId>cucumber-junit</artifactId>")
        .containing("<artifactId>cucumber-java</artifactId>")
        .containing("<artifactId>cucumber-spring</artifactId>")
        .containing("<artifactId>junit-vintage-engine</artifactId>")
        .containing(
          """
              <dependency>
                <groupId>tech.jhipster.lite</groupId>
                <artifactId>jhlite</artifactId>
                <version>${jhlite.version}</version>
              </dependency>
          """
        )
        .containing(
          """
              <dependency>
                <groupId>tech.jhipster.lite</groupId>
                <artifactId>jhlite</artifactId>
                <version>${jhlite.version}</version>
                <classifier>tests</classifier>
                <scope>test</scope>
                <type>test-jar</type>
              </dependency>
          """
        )
        .and()
      .hasFile("src/main/resources/config/application.properties")
        .containing("server.port=9000")
        .containing("application.exception.package=org.,java.,net.,jakarta.,com.,io.,de.,tech.jhipster.lite,com.jhipster.test")
        .containing("spring.jackson.default-property-inclusion=non_null")
        .containing("jhlite-hidden-resources.tags=banner")
        .containing("jhlite-hidden-resources.slugs=custom-jhlite")
        .and()
      .hasFile("src/test/resources/config/application-test.properties")
        .containing("server.port=0")
        .containing("spring.main.allow-bean-definition-overriding=true")
        .and()
      .hasFile("src/main/java/com/jhipster/test/MyappApp.java")
        .containing("import tech.jhipster.lite.JHLiteApp;")
        .containing("@SpringBootApplication(scanBasePackageClasses = { JHLiteApp.class, MyappApp.class })")
        .and()
      .hasPrefixedFiles("documentation", "module-creation.md", "cucumber.md")
      .doNotHaveFiles(
        "src/main/java/tech/jhipster/test/security/infrastructure/primary/CorsFilterConfiguration.java",
        "src/main/java/tech/jhipster/test/security/infrastructure/primary/CorsProperties.java",
        "src/test/java/tech/jhipster/test/security/infrastructure/primary/CorsFilterConfigurationIT.java"
      )
      .hasFile("src/test/java/com/jhipster/test/cucumber/CucumberTest.java")
        .containing("glue = { \"com.jhipster.test\", \"tech.jhipster.lite.module.infrastructure.primary\" },")
        .and()
      .hasFile("src/test/java/com/jhipster/test/cucumber/CucumberConfiguration.java")
        .containing("import com.jhipster.test.MyappApp;")
        .and()
      .hasFiles("src/test/java/com/jhipster/test/cucumber/CucumberRestTemplate.java")
      .hasFiles("src/test/features/.gitkeep");
    //@formatter:on
  }

  private ModuleFile mainAppFile() {
    return file("src/test/resources/projects/files/MainApp.java", "src/main/java/com/jhipster/test/MyappApp.java");
  }
}
