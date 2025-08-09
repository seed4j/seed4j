package com.seed4j.generator.client.vue.security.jwt.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class VueJwtModuleFactoryTest {

  private static final VueJwtModuleFactory factory = new VueJwtModuleFactory();

  @Test
  void shouldBuildVueJwtModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhipster")
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), mainFile())
      .hasFiles("documentation/vue-jwt-authentication-components.md")
      .hasFiles("src/main/webapp/app/auth/application/AuthProvider.ts")
      .hasFiles("src/main/webapp/app/auth/domain/AuthRepository.ts")
      .hasFiles("src/main/webapp/app/auth/domain/AuthenticatedUser.ts")
      .hasFiles("src/main/webapp/app/auth/domain/Authentication.ts")
      .hasFiles("src/main/webapp/app/auth/domain/LoginCredentials.ts")
      .hasFiles("src/main/webapp/app/auth/infrastructure/secondary/JwtAuthRepository.ts")
      .hasFiles("src/main/webapp/app/auth/infrastructure/secondary/RestAuthentication.ts")
      .hasFiles("src/main/webapp/app/auth/infrastructure/secondary/RestLoginCredentials.ts")
      .hasFile("src/main/webapp/app/main.ts")
        .containing("""
          import { provideForAuth } from '@/auth/application/AuthProvider';
          """
        )
        .containing("""
          import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
          """
        )
        .containing("""
          import axios from 'axios';
          """
        )
        .containing("""
          const axiosInstance = axios.create({ baseURL: 'http://localhost:8080/' });
          """
        )
        .containing("""
          const axiosHttp = new AxiosHttp(axiosInstance);
          """
        )
        .containing("""
          provideForAuth(axiosHttp);
          """
        )
        .and()
      .hasFiles("src/test/webapp/unit/auth/application/AuthProvider.spec.ts")
      .hasFiles("src/test/webapp/unit/auth/infrastructure/secondary/JwtAuthRepository.spec.ts")
      .hasFiles("src/test/webapp/unit/shared/http/infrastructure/secondary/AxiosHttpStub.ts");
    // @formatter:on
  }

  private static ModuleFile mainFile() {
    return file("src/test/resources/projects/vue/main.ts.mustache", "src/main/webapp/app/main.ts");
  }
}
