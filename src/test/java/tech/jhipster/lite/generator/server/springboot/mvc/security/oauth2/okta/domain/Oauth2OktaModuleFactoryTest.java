package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.okta.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class Oauth2OktaModuleFactoryTest {

  private static final OAuth2OktaModuleFactory factory = new OAuth2OktaModuleFactory();

  @Test
  @DisplayName("should build OAuth2 okta module")
  void shouldBuildOAuth2OktaModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .put("oktaDomain", "dev-123456.okta.com")
      .put("oktaClientId", "my-client-id")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/config/application-okta.yml")
      .containing(
        """
        spring:
          security:
            oauth2:
              client:
                provider:
                  oidc:
                    issuer-uri: https://dev-123456.okta.com/oauth2/default
                registration:
                  oidc:
                    client-id: my-client-id
        """
      )
      .and()
      .hasFile("documentation/okta.md")
      .and()
      .hasFile("documentation/images/security-add-claim.png")
      .and()
      .hasFile(".gitignore")
      .containing(
        """
        # OAuth 2.0
        okta.sh\
        """
      )
      .and()
      .hasFile("okta.sh")
      .containing("export SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_OIDC_CLIENT_SECRET=my-client-secret-which-should-be-changed");
  }
}
