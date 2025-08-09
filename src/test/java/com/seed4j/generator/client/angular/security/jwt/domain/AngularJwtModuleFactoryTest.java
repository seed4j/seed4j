package com.seed4j.generator.client.angular.security.jwt.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class AngularJwtModuleFactoryTest {

  private static final AngularJwtModuleFactory factory = new AngularJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhipster")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, appRoutingFile(), appMainFile())
      .hasPrefixedFiles(
        "src/main/webapp/app/auth",
        "account.model.ts",
        "account.model.spec.ts",
        "account.service.ts",
        "account.service.spec.ts",
        "auth.interceptor.ts",
        "auth.interceptor.spec.ts",
        "auth-jwt.service.spec.ts"
      )
      .hasPrefixedFiles(
        "src/main/webapp/app/login",
        "login.service.ts",
        "login.service.spec.ts",
        "login.model.ts",
        "login.css",
        "login.html",
        "login.ts"
      )
      .hasFile("src/main/webapp/app/login/login.spec.ts")
      .and()
      .hasFile("src/main/webapp/app/app.route.ts")
      .containing(
        """
          {
            path: '',
            loadComponent: () => import('./login/login'),
          },
        """
      );
  }

  private static ModuleFile appRoutingFile() {
    return file("src/test/resources/projects/angular/app.route.ts", "src/main/webapp/app/app.route.ts");
  }

  private static ModuleFile appMainFile() {
    return file("src/test/resources/projects/angular/main.ts", "src/main/webapp/main.ts");
  }
}
