package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocOauth2ModuleFactoryTest {

  private static final SpringdocOauth2ModuleFactory factory = new SpringdocOauth2ModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .put("keycloakRealmName", "beer")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("src/main/java/tech/jhipster/jhlitest/wire/springdoc/infrastructure/primary/SpringdocOAuth2Configuration.java")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        springdoc:
          oauth2:
            authorization-url: http://localhost:9080/realms/beer/protocol/openid-connect/auth
          swagger-ui:
            oauth:
              client-id: web_app
              realm: beer
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        springdoc:
          oauth2:
            authorization-url: http://localhost:9080/realms/beer/protocol/openid-connect/auth
          swagger-ui:
            oauth:
              client-id: web_app
              realm: beer
        """
      );
    // @formatter:on
  }
}
