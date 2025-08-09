package com.seed4j.generator.server.springboot.apidocumentation.springdocokta.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocOktaModuleFactoryTest {

  private static final SpringdocOktaModuleFactory factory = new SpringdocOktaModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("oktaDomain", "dev-123456.okta.com")
      .put("oktaClientId", "my-client-id")
      .build();

    SeedModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module)
      .hasFile("src/main/resources/config/application-okta.yml")
      .containing(
        """
        springdoc:
          oauth2:
            authorization-url: https://dev-123456.okta.com/oauth2/default/v1/authorize?nonce="jhipster"
          swagger-ui:
            oauth:
              client-id: my-client-id
              realm: jhipster
              scopes:
              - openid
              - profile
              - email
        """
      );
    // @formatter:on
  }
}
