package com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class Oauth2OktaModuleFactoryTest {

  private static final OAuth2OktaModuleFactory factory = new OAuth2OktaModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
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
