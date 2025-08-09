package com.seed4j.generator.client.vue.security.oauth2_keycloak.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class VueOAuth2KeycloakModuleFactoryTest {

  private static final VueOAuth2KeycloakModuleFactory factory = new VueOAuth2KeycloakModuleFactory();

  @Test
  void shouldBuildVueOAuth2KeycloakModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhipster")
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), mainFile())
      .hasFiles("documentation/vue-oauth2-keycloak-authentication-components.md")
      .hasFile("package.json")
        .containing(nodeDependency("keycloak-js"))
        .and()
      .hasFiles("src/main/webapp/app/auth/application/AuthProvider.ts")
      .hasFiles("src/main/webapp/app/auth/domain/AuthRepository.ts")
      .hasFiles("src/main/webapp/app/auth/domain/AuthenticatedUser.ts")
      .hasFiles("src/main/webapp/app/auth/infrastructure/secondary/KeycloakAuthRepository.ts")
      .hasFiles("src/main/webapp/app/auth/infrastructure/secondary/KeycloakHttp.ts")
      .hasFile("src/main/webapp/app/main.ts")
        .containing("""
          import { provideForAuth } from '@/auth/application/AuthProvider';
          import { KeycloakHttp } from '@/auth/infrastructure/secondary/KeycloakHttp';
          import Keycloak from 'keycloak-js';
          // seed4j-needle-main-ts-import\
          """
        )
        .containing("""
          const keycloakHttp = new KeycloakHttp(
            new Keycloak({
              url: 'http://localhost:9080',
              realm: 'jhipster',
              clientId: 'web_app',
            }),
          );

          provideForAuth(keycloakHttp);
          // seed4j-needle-main-ts-provider\
          """
        )
        .and()
      .hasFiles("src/test/webapp/unit/auth/application/AuthProvider.spec.ts")
      .hasFiles("src/test/webapp/unit/auth/infrastructure/secondary/KeycloakAuthRepository.spec.ts")
      .hasFiles("src/test/webapp/unit/auth/infrastructure/secondary/KeycloakHttp.spec.ts")
      .hasFiles("src/test/webapp/unit/auth/infrastructure/secondary/KeycloakHttpStub.ts")
      .hasFiles("src/test/webapp/unit/auth/infrastructure/secondary/KeycloakStub.ts");
    // @formatter:on
  }

  private static ModuleFile mainFile() {
    return file("src/test/resources/projects/vue/main.ts.mustache", "src/main/webapp/app/main.ts");
  }
}
