package tech.jhipster.lite.generator.client.angular.security.jwt.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class AngularJwtModuleFactoryTest {

  private static final AngularJwtModuleFactory factory = new AngularJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhipster")
      .build();

    JHipsterModule module = factory.buildModule(properties);

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
        "login.component.css",
        "login.component.html",
        "login.component.ts"
      )
      .hasFile("src/main/webapp/app/login/login.component.spec.ts")
      .and()
      .hasFile("src/main/webapp/app/app.route.ts")
      .containing(
        """
          {
            path: '',
            loadComponent: () => import('./login/login.component'),
            title: 'Login',
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
