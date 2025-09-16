package com.seed4j.generator.server.springboot.cucumberauthentication.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CucumberAuthenticationModuleFactoryTest {

  private static final CucumberAuthenticationModuleFactory factory = new CucumberAuthenticationModuleFactory();

  @Test
  void shouldBuildOauthAuthenticationModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildOauth2Module(properties);

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
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildJWTModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/cucumber-authentication.md")
      .hasJavaTests("com/seed4j/growth/shared/authentication/infrastructure/primary/AuthenticationSteps.java");
  }
}
