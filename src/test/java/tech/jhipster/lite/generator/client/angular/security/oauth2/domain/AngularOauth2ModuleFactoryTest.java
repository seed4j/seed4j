package tech.jhipster.lite.generator.client.angular.security.oauth2.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class AngularOauth2ModuleFactoryTest {

  private static final AngularOauth2ModuleFactory factory = new AngularOauth2ModuleFactory();

  @Test
  void shouldBuildModuleWithEmptyAngularFile() {
    assertAngularOAuthModule(emptyAngularJsonFile())
      .hasFile("angular.json")
      .containing("\"allowedCommonJsDependencies\": [\"keycloak-js\"]");
  }

  @Test
  void shouldBuildModuleWithAngularFileWithAllowedDependencies() {
    assertAngularOAuthModule(angularJsonFile())
      .hasFile("angular.json")
      .containing("\"allowedCommonJsDependencies\": [\"dummy.js\", \"keycloak-js\"]");
  }

  private static JHipsterModuleAsserter assertAngularOAuthModule(ModuleFile angularJson) {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    return assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      environmentFile(),
      prodEnvironmentFile(),
      angularJson,
      appComponentHtmlFile(),
      appComponentFile(),
      appComponentSpecFile(),
      mainFile()
    )
      .hasFile("package.json")
      .containing(nodeDependency("keycloak-js"))
      .and()
      .hasPrefixedFiles(
        "src/main/webapp/app/auth",
        "oauth2-auth.service.ts",
        "oauth2-auth.service.spec.ts",
        "http-auth.interceptor.ts",
        "http-auth.interceptor.spec.ts"
      )
      .hasPrefixedFiles("src/main/webapp/app/login", "login.component.html", "login.component.ts", "login.component.spec.ts")
      .hasFile("src/main/webapp/environments/environment.ts")
      .containing(
        """
          keycloak: {
            url: 'http://localhost:9080',
            realm: 'jhipster',
            client_id: 'web_app'
          },
        """
      )
      .and()
      .hasFile("src/main/webapp/environments/environment.prod.ts")
      .containing(
        """
          keycloak: {
            url: 'http://localhost:9080',
            realm: 'jhipster',
            client_id: 'web_app'
          },
        """
      )
      .and()
      .hasFile("src/main/webapp/app/app.component.html")
      .containing("<jhi-login></jhi-login>")
      .and()
      .hasFile("src/main/webapp/app/app.component.spec.ts")
      .containing("import { By } from '@angular/platform-browser';")
      .containing("import LoginComponent from './login/login.component';")
      .containing("LoginComponent")
      .containing("it('should display login component', () => {")
      .and();
  }

  private static ModuleFile environmentFile() {
    return file("src/test/resources/projects/angular/environment.ts", "src/main/webapp/environments/environment.ts");
  }

  private static ModuleFile prodEnvironmentFile() {
    return file("src/test/resources/projects/angular/environment.prod.ts", "src/main/webapp/environments/environment.prod.ts");
  }

  private static ModuleFile angularJsonFile() {
    return file("src/test/resources/projects/angular/angular.json", "angular.json");
  }

  private static ModuleFile emptyAngularJsonFile() {
    return file("src/test/resources/projects/angular/empty-angular.json", "angular.json");
  }

  private static ModuleFile appComponentHtmlFile() {
    return file("src/test/resources/projects/angular/app.component.html", "src/main/webapp/app/app.component.html");
  }

  private static ModuleFile appComponentSpecFile() {
    return file("src/test/resources/projects/angular/app.component.spec.ts", "src/main/webapp/app/app.component.spec.ts");
  }

  private static ModuleFile appComponentFile() {
    return file("src/test/resources/projects/angular/app.component.ts", "src/main/webapp/app/app.component.ts");
  }

  private static ModuleFile mainFile() {
    return file("src/test/resources/projects/angular/main.ts", "src/main/webapp/main.ts");
  }
}
