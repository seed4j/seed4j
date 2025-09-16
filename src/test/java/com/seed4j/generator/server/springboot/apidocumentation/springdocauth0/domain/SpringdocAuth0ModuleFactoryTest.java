package com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocAuth0ModuleFactoryTest {

  private static final SpringdocAuth0ModuleFactory factory = new SpringdocAuth0ModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("auth0Domain", "dev-123456.us.auth0.com")
      .put("auth0ClientId", "my-client-id")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module)
      .hasFile("src/main/resources/config/application-auth0.yml")
      .containing(
        """
        springdoc:
          oauth2:
            authorization-url: https://dev-123456.us.auth0.com/authorize?audience=https://dev-123456.us.auth0.com/api/v2/
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
