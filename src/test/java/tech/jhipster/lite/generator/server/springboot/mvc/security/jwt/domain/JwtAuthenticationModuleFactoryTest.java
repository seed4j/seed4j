package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JwtAuthenticationModuleFactoryTest {

  private static final JwtAuthenticationModuleFactory factory = new JwtAuthenticationModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("jhipster")
      .build();

    JHipsterModule module = factory.buildModule(properties);

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
              <version>${jjwt.version}</version>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-impl</artifactId>
              <version>${jjwt.version}</version>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt-jackson</artifactId>
              <version>${jjwt.version}</version>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.security</groupId>
              <artifactId>spring-security-test</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/java/com/jhipster/test/authentication/package-info.java")
      .hasPrefixedFiles("src/main/java/com/jhipster/test/authentication/domain", "Role.java", "Roles.java", "Username.java")
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/authentication/infrastructure/primary",
        "AuthenticatedUser.java",
        "AuthenticationException.java",
        "AuthenticationExceptionAdvice.java",
        "AuthenticationTokenReader.java",
        "JwtAuthenticationProperties.java",
        "JWTConfigurer.java",
        "JWTFilter.java",
        "JwtReader.java",
        "NotAuthenticatedUserException.java",
        "SecurityConfiguration.java",
        "UnknownAuthenticationException.java"
      )
      .hasPrefixedFiles("src/test/java/com/jhipster/test/authentication/domain", "RolesTest.java", "RoleTest.java", "UsernameTest.java")
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/authentication/infrastructure/primary",
        "AuthenticatedUserTest.java",
        "AuthenticationExceptionAdviceIT.java",
        "JWTFilterTest.java",
        "JwtReaderTest.java",
        "AccountExceptionResource.java"
      )
      .hasFile("src/test/java/com/jhipster/test/IntegrationTest.java")
      .containing("@WithMockUser")
      .containing("import org.springframework.security.test.context.support.WithMockUser;")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("application.security.jwt-base64-secret=")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("application.security.jwt-base64-secret=")
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.springframework.security\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.springframework.security\" level=\"WARN\" />");
  }

  private ModuleFile integrationTestFile() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/jhipster/test/IntegrationTest.java");
  }
}
