package com.seed4j.generator.server.springboot.mvc.security.jwt.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.ModuleFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.file;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.logbackFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.testLogbackFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JwtAuthenticationModuleFactoryTest {

  private static final JwtAuthenticationModuleFactory factory = new JwtAuthenticationModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("seed4j")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), integrationTestFile(), logbackFile(), testLogbackFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-security</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-api</artifactId>
              <version>${json-web-token.version}</version>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-impl</artifactId>
              <version>${json-web-token.version}</version>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-jackson</artifactId>
              <version>${json-web-token.version}</version>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-security-test</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/java/com/seed4j/growth/shared/authentication/package-info.java")
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/shared/authentication/domain", "Role.java", "Roles.java", "Username.java")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/authentication/application",
        "AuthenticatedUser.java",
        "NotAuthenticatedUserException.java",
        "AuthenticationException.java",
        "UnknownAuthenticationException.java"
      )
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/authentication/infrastructure/primary",
        "AuthenticationExceptionAdvice.java",
        "AuthenticationTokenReader.java",
        "JwtAuthenticationProperties.java",
        "JWTConfigurer.java",
        "JWTFilter.java",
        "JwtReader.java",
        "SecurityConfiguration.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/authentication/domain",
        "RolesTest.java",
        "RoleTest.java",
        "UsernameTest.java"
      )
      .hasFiles("src/test/java/com/seed4j/growth/shared/authentication/application/AuthenticatedUserTest.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary",
        "AuthenticationExceptionAdviceIT.java",
        "JWTFilterTest.java",
        "JwtReaderTest.java",
        "AccountExceptionResource.java"
      )
      .hasFile("src/test/java/com/seed4j/growth/IntegrationTest.java")
      .containing("@WithMockUser")
      .containing("import org.springframework.security.test.context.support.WithMockUser;")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        application:
          security:
            jwt-base64-secret:"""
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        application:
          security:
            jwt-base64-secret:"""
      )
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.springframework.security\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.springframework.security\" level=\"WARN\" />");
  }

  @Test
  void shouldBuildModuleWithJwtBase64Secret() {
    String jwtBase64Secret = "Y2EyZjQ2YmNmZjMwMTE5YjcxOTBjYzZiYWVjZjY0NzZlMzNmNjY5MjgwMjUxZDNjOTA3N2M5YjAyYTg3ODEzMA==";
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("seed4j")
      .put("jwtBase64Secret", jwtBase64Secret)
      .build();

    Seed4JModule module = factory.buildModule(properties);

    String config =
      """
        application:
          security:
            jwt-base64-secret:"""
      + " "
      + jwtBase64Secret;
    assertThatModuleWithFiles(module, pomFile(), integrationTestFile(), logbackFile(), testLogbackFile())
      .hasFile("src/main/resources/config/application.yml")
      .containing(config)
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(config);
  }

  private ModuleFile integrationTestFile() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/seed4j/growth/IntegrationTest.java");
  }
}
