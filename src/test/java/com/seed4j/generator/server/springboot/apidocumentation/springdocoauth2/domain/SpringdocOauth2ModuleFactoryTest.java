package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocOauth2ModuleFactoryTest {

  private static final SpringdocOauth2ModuleFactory factory = new SpringdocOauth2ModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("keycloakRealmName", "beer")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocOAuth2Configuration.java")
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
