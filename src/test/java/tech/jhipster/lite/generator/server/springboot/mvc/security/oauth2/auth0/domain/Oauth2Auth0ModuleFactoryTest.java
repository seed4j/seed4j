package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.auth0.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class Oauth2Auth0ModuleFactoryTest {

  private static final OAuth2Auth0ModuleFactory factory = new OAuth2Auth0ModuleFactory();

  @Test
  @DisplayName("should build OAuth2 module")
  void shouldBuildOAuth2Module() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .put("auth0Domain", "dev-123456.us.auth0.com")
      .put("auth0ClientId", "my-client-id")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/config/application-auth0.yml")
      .containing(
        """
        application:
          security:
            oauth2:
              audience:
              - account
              - api://default
              - https://dev-123456.us.auth0.com/api/v2/
        spring:
          security:
            oauth2:
              client:
                provider:
                  oidc:
                    issuer-uri: https://dev-123456.us.auth0.com/
                registration:
                  oidc:
                    client-id: my-client-id
        """
      )
      .and()
      .hasFile("documentation/auth0.md")
      .and()
      .hasFile(".gitignore")
      .containing(
        """
        # OAuth 2.0
        auth0.sh\
        """
      )
      .and()
      .hasFile("auth0.sh")
      .containing("export SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_OIDC_CLIENT_SECRET=my-client-secret-which-should-be-changed");
  }
}
