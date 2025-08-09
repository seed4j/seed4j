package com.seed4j.generator.server.springboot.cucumberauthentication.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CucumberAuthenticationModuleFactoryTest {

  private static final CucumberAuthenticationModuleFactory factory = new CucumberAuthenticationModuleFactory();

  @Test
  void shouldBuildOauthAuthenticationModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildOauth2Module(properties);

    assertThatModuleWithFiles(module, pomFile(), cucumberConfiguration())
      .hasFiles("documentation/cucumber-authentication.md")
      .hasFile("src/test/java/com/seed4j/growth/cucumber/CucumberConfiguration.java")
      .containing("classes = { MyappApp.class, TestSecurityConfiguration.class, CucumberAuthenticationConfiguration.class")
      .containing("import com.seed4j.growth.shared.authentication.infrastructure.primary.TestSecurityConfiguration;")
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
      .hasJavaTests("com/seed4j/growth/cucumber/CucumberAuthenticationConfiguration.java")
      .hasJavaTests("com/seed4j/growth/shared/authentication/infrastructure/primary/AuthenticationSteps.java");
  }

  private ModuleFile cucumberConfiguration() {
    return new ModuleFile(
      "src/test/resources/projects/cucumber/CucumberConfiguration.java",
      "src/test/java/com/seed4j/growth/cucumber/CucumberConfiguration.java"
    );
  }

  @Test
  void shouldBuildJwtAuthenticationModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildJWTModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/cucumber-authentication.md")
      .hasJavaTests("com/seed4j/growth/shared/authentication/infrastructure/primary/AuthenticationSteps.java");
  }
}
