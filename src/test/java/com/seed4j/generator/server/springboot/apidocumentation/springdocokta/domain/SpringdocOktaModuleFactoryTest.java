package com.seed4j.generator.server.springboot.apidocumentation.springdocokta.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocOktaModuleFactoryTest {

  private static final SpringdocOktaModuleFactory factory = new SpringdocOktaModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("oktaDomain", "dev-123456.okta.com")
      .put("oktaClientId", "my-client-id")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module)
      .hasFile("src/main/resources/config/application-okta.yml")
      .containing(
        """
        springdoc:
          oauth2:
            authorization-url: https://dev-123456.okta.com/oauth2/default/v1/authorize?nonce="seed4j"
          swagger-ui:
            oauth:
              client-id: my-client-id
              realm: seed4j
              scopes:
              - openid
              - profile
              - email
        """
      );
    // @formatter:on
  }
}
