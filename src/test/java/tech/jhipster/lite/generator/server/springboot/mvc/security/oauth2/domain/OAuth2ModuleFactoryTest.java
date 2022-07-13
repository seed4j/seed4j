package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class OAuth2ModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private OAuth2ModuleFactory factory;

  @Test
  void shouldCreateOAuth2Module() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get("jboss/keycloak")).thenReturn(new DockerImage("jboss/keycloak", "1.1.1"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(
      module,
      pomFile(),
      file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/jhipster/test/IntegrationTest.java")
    )
      .hasPrefixedFiles("src/main/java/com/jhipster/test/authentication/domain", "Role.java", "Roles.java", "Username.java")
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/authentication/infrastructure/primary",
        "ApplicationSecurityProperties.java",
        "AudienceValidator.java",
        "AuthenticatedUser.java",
        "AuthenticationException.java",
        "AuthenticationExceptionAdvice.java",
        "Claims.java",
        "CustomClaimConverter.java",
        "JwtGrantedAuthorityConverter.java",
        "NotAuthenticatedUserException.java",
        "OAuth2Configuration.java",
        "SecurityConfiguration.java",
        "UnknownAuthenticationException.java"
      )
      .hasPrefixedFiles("src/test/java/com/jhipster/test/authentication/domain", "RolesTest.java", "RoleTest.java", "UsernameTest.java")
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/authentication/infrastructure/primary",
        "AccountExceptionResource.java",
        "ApplicationSecurityPropertiesTest.java",
        "AudienceValidatorTest.java",
        "AuthenticatedUserTest.java",
        "AuthenticationExceptionAdviceIT.java",
        "ClaimsTest.java",
        "CustomClaimConverterIT.java",
        "FakeRequestAttributes.java",
        "JwtGrantedAuthorityConverterTest.java",
        "SecurityConfigurationIT.java",
        "SecurityConfigurationTest.java",
        "TestSecurityConfiguration.java",
        "WithUnauthenticatedMockUser.java"
      )
      .hasFile("src/main/docker/keycloak.yml")
      .containing("jboss/keycloak:1.1.1")
      .and()
      .hasFile("src/main/docker/keycloak-realm-config/jhipster-realm.json")
      .containing("1.1.1")
      .and()
      .hasFile("src/main/docker/keycloak-realm-config/jhipster-users-0.json")
      .and()
      .hasFile("src/main/java/com/jhipster/test/authentication/package-info.java")
      .and()
      .hasFile("pom.xml")
      .containing("spring-boot-starter-security")
      .containing("spring-boot-starter-oauth2-client")
      .containing("spring-security-test")
      .containing("spring-boot-starter-oauth2-resource-server")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.security.oauth2.client.provider.oidc.issuer-uri=http://localhost:9080/auth/realms/jhipster")
      .containing("spring.security.oauth2.client.registration.oidc.client-id=web_app")
      .containing("spring.security.oauth2.client.registration.oidc.client-secret=web_app")
      .containing("spring.security.oauth2.client.registration.oidc.scope=openid,profile,email")
      .containing("application.security.oauth2.audience=account,api://default")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("spring.main.allow-bean-definition-overriding=true")
      .containing("spring.security.oauth2.client.provider.oidc.issuer-uri=http://DO_NOT_CALL:9080/auth/realms/jhipster")
      .and()
      .hasFile("src/test/java/com/jhipster/test/IntegrationTest.java")
      .containing("@SpringBootTest(classes = { MyappApp.class, TestSecurityConfiguration.class })")
      .containing("@WithMockUser")
      .containing("import org.springframework.security.test.context.support.WithMockUser;")
      .containing("import com.jhipster.test.authentication.infrastructure.primary.TestSecurityConfiguration;");
  }
}
