package com.seed4j.generator.server.springboot.mvc.security.oauth2.core.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class OAuth2ModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private OAuth2ModuleFactory factory;

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .put("keycloakRealmName", "beer")
      .build();

    when(dockerImages.get("quay.io/keycloak/keycloak")).thenReturn(new DockerImageVersion("quay.io/keycloak/keycloak", "1.1.1"));

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), integrationTestFile(), readmeFile())
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
        "ApplicationSecurityProperties.java",
        "AudienceValidator.java",
        "AuthenticationExceptionAdvice.java",
        "Claims.java",
        "CustomClaimConverter.java",
        "JwtGrantedAuthorityConverter.java",
        "OAuth2Configuration.java",
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
        "AccountExceptionResource.java",
        "ApplicationSecurityPropertiesTest.java",
        "AudienceValidatorTest.java",
        "AuthenticationExceptionAdviceIT.java",
        "ClaimsTest.java",
        "CustomClaimConverterTest.java",
        "FakeRequestAttributes.java",
        "JwtGrantedAuthorityConverterTest.java",
        "SecurityConfigurationIT.java",
        "TestSecurityConfiguration.java",
        "WithUnauthenticatedMockUser.java"
      )
      .hasFile("src/main/docker/keycloak.yml")
      .containing("quay.io/keycloak/keycloak:1.1.1")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/keycloak.yml")
      .and()
      .hasFile("src/main/docker/keycloak-realm-config/beer-realm.json")
      .containing("1.1.1")
      .and()
      .hasFile("src/main/java/com/seed4j/growth/shared/authentication/package-info.java")
      .and()
      .hasFile("pom.xml")
      .containing("spring-boot-starter-security")
      .containing("spring-boot-starter-oauth2-client")
      .containing("spring-security-test")
      .containing("spring-boot-starter-oauth2-resource-server")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        // language=yaml
        """
        application:
          security:
            oauth2:
              audience: account,api://default
        spring:
          security:
            oauth2:
              client:
                provider:
                  oidc:
                    issuer-uri: http://localhost:9080/realms/beer
                registration:
                  oidc:
                    client-id: web_app
                    client-secret: web_app
                    scope: openid,profile,email
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        // language=yaml
        """
        spring:
          main:
            allow-bean-definition-overriding: true
          security:
            oauth2:
              client:
                provider:
                  oidc:
                    issuer-uri: http://DO_NOT_CALL:9080/realms/beer
        """
      )
      .and()
      .hasFile("src/test/java/com/seed4j/growth/IntegrationTest.java")
      .containing("@SpringBootTest(classes = { MyappApp.class, TestSecurityConfiguration.class })")
      .containing("import com.seed4j.growth.shared.authentication.infrastructure.primary.TestSecurityConfiguration;")
      .containing("@WithMockUser")
      .containing("import org.springframework.security.test.context.support.WithMockUser;")
      .and()
      .hasFile("README.md")
      .containing("docker compose -f src/main/docker/keycloak.yml up -d");
  }

  private static ModuleFile integrationTestFile() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/seed4j/growth/IntegrationTest.java");
  }
}
