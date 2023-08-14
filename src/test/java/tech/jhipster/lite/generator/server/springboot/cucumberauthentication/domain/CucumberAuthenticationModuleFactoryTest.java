package tech.jhipster.lite.generator.server.springboot.cucumberauthentication.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class CucumberAuthenticationModuleFactoryTest {

  private static final CucumberAuthenticationModuleFactory factory = new CucumberAuthenticationModuleFactory();

  @Test
  void shouldBuildOauthAuthenticationModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildOauth2Module(properties);

    assertThatModuleWithFiles(module, pomFile(), cucumberConfiguration())
      .hasFiles("documentation/cucumber-authentication.md")
      .hasFile("src/test/java/com/jhipster/test/cucumber/CucumberConfiguration.java")
      .containing("classes = { MyappApp.class, TestSecurityConfiguration.class, CucumberAuthenticationConfiguration.class")
      .containing("import com.jhipster.test.shared.authentication.infrastructure.primary.TestSecurityConfiguration;")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-api</artifactId>
              <version>${json-web-token.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-impl</artifactId>
              <version>${json-web-token.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-jackson</artifactId>
              <version>${json-web-token.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasJavaTests("com/jhipster/test/cucumber/CucumberAuthenticationConfiguration.java")
      .hasJavaTests("com/jhipster/test/shared/authentication/infrastructure/primary/AuthenticationSteps.java");
  }

  private ModuleFile cucumberConfiguration() {
    return new ModuleFile(
      "src/test/resources/projects/cucumber/CucumberConfiguration.java",
      "src/test/java/com/jhipster/test/cucumber/CucumberConfiguration.java"
    );
  }

  @Test
  void shouldBuildJwtAuthenticationModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildJWTModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/cucumber-authentication.md")
      .hasJavaTests("com/jhipster/test/shared/authentication/infrastructure/primary/AuthenticationSteps.java");
  }
}
