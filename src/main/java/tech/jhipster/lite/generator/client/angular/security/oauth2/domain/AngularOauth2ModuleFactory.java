package tech.jhipster.lite.generator.client.angular.security.oauth2.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.regex.Pattern;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.module.domain.replacement.RegexReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public class AngularOauth2ModuleFactory {

  private static final Pattern PROVIDERS_PATTERN = Pattern.compile("(providers: *\\[)");
  private static final ElementReplacer EXISTING_PROVIDERS_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> PROVIDERS_PATTERN.matcher(contentBeforeReplacement).find(),
    PROVIDERS_PATTERN
  );

  private static final ElementReplacer ENVIRONMENT_NEEDLE = lineAfterRegex("export const environment *= *\\{");
  private static final String KEYCLOAK_ENVIRONMENT =
    """
      keycloak: {
        url: 'http://localhost:9080',
        realm: 'jhipster',
        client_id: 'web_app'
      },
      """;

  private static final Pattern EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile(
    "(\"allowedCommonJsDependencies\": *\\[\\s*)\\]"
  );
  private static final ElementReplacer EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN.matcher(contentBeforeReplacement).find(),
    EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final Pattern FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile(
    "(\"allowedCommonJsDependencies\": *\\[[^]]+)\\]"
  );
  private static final ElementReplacer FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN.matcher(contentBeforeReplacement).find(),
    FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final Pattern FILLED_STANDALONE_PATTERN = Pattern.compile("(imports: *\\[[^]]+)\\]");
  private static final ElementReplacer FILLED_STANDALONE_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> FILLED_STANDALONE_PATTERN.matcher(contentBeforeReplacement).find(),
    FILLED_STANDALONE_PATTERN
  );

  private static final ElementReplacer MENU_NEEDLE = lineAfterRegex("<span.+id=\\\"menu-space-separator\\\".*></span>");

  private static final String TEST_IMPORTS =
    """
      import { By } from '@angular/platform-browser';
      import { Oauth2AuthService } from "./auth/oauth2-auth.service";
      import LoginComponent from './login/login.component';
      """;
  private static final ElementReplacer TEST_NEEDLE = lineAfterRegex("^\\s+it\\('should have appName',[^}]+\\}\\);");

  private static final String LOGIN_IMPORT = """
      import LoginComponent from './login/login.component';
      """;

  private static final String OAUTH2_AUTH_SERVICE_IMPORT =
    """
      import { Oauth2AuthService } from './auth/oauth2-auth.service';
      """;

  private static final ElementReplacer APPNAME_NEEDLE = lineAfterRegex("appName = '';");

  private static final String INJECT_OAUTH2_AUTH_SERVICE = """
        private oauth2AuthService = inject(Oauth2AuthService);\
      """;

  private static final String INIT_AUTHENTICATION = """
          this.oauth2AuthService.initAuthentication();\
      """;

  private static final String INJECT_IMPORT = """
      import { Component, inject, OnInit } from '@angular/core';
      """;

  private static final ElementReplacer INJECT_NEEDLE = text("import { Component, OnInit } from '@angular/core';");

  private static final ElementReplacer BEFORE_EACH_NEEDLE = lineAfterRegex("comp = fixture.componentInstance;");

  private static final String TESTBED_INJECT_OAUTH2_AUTH_SERVICE =
    """
          oauth2AuthService = TestBed.inject(Oauth2AuthService);\
      """;

  private static final ElementReplacer TEST_APP_COMPONENT = lineAfterRegex("let comp: AppComponent;");

  private static final String DECLARE_INJECT_OAUTH2_AUTH_SERVICE = """
        let oauth2AuthService: Oauth2AuthService;\
      """;

  private static final String LOGIN_COMPONENT_TEST =
    """

      it('should display login component', () => {
        fixture.detectChanges();

        expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
      });
      """;

  private static final String HTTP_AUTH_INTERCEPTOR_IMPORT =
    """
      import { HttpAuthInterceptor } from './app/auth/http-auth.interceptor';
      """;

  private static final JHipsterSource SOURCE = from("client/angular/security/oauth2/src/main/webapp/app");

  private static final JHipsterDestination APP_DESTINATION = to("src/main/webapp/app");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("keycloak-js"), VersionSource.ANGULAR)
        .and()
      .files()
        .batch(SOURCE.append("auth"), APP_DESTINATION.append("auth"))
          .addFile("oauth2-auth.service.ts")
          .addFile("oauth2-auth.service.spec.ts")
          .addFile("http-auth.interceptor.ts")
          .addFile("http-auth.interceptor.spec.ts")
          .and()
        .batch(SOURCE.append("login"), APP_DESTINATION.append("login"))
          .addFile("login.component.html")
          .addFile("login.component.ts")
          .addFile("login.component.spec.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/environments/environment.ts"))
          .add(ENVIRONMENT_NEEDLE, keycloakEnvironment(indentation))
          .and()
        .in(path("src/main/webapp/environments/environment.prod.ts"))
          .add(ENVIRONMENT_NEEDLE, keycloakEnvironment(indentation))
          .and()
        .in(path("angular.json"))
          .add(FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1, \"keycloak-js\"]")
          .add(EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1\"keycloak-js\"]")
          .and()
        .in(path("src/main/webapp/main.ts"))
          .add(EXISTING_PROVIDERS_NEEDLE, "providers: [{ provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true },")
          .add(fileStart(), HTTP_AUTH_INTERCEPTOR_IMPORT)
          .and()
        .in(path("src/main/webapp/app/app.component.ts"))
          .add(FILLED_STANDALONE_NEEDLE, "$1, LoginComponent]")
          .add(fileStart(), OAUTH2_AUTH_SERVICE_IMPORT)
          .add(fileStart(), LOGIN_IMPORT)
          .add(INJECT_NEEDLE ,INJECT_IMPORT)
          .add(APPNAME_NEEDLE, INJECT_OAUTH2_AUTH_SERVICE)
          .add(lineAfterRegex("this.appName = '" + properties.projectBaseName().name() + "';"), INIT_AUTHENTICATION)
          .and()
        .in(path("src/main/webapp/app/app.component.spec.ts"))
          .add(fileStart(), TEST_IMPORTS)
          .add(TEST_NEEDLE, LOGIN_COMPONENT_TEST.indent(indentation.spacesCount() * 2))
          .add(BEFORE_EACH_NEEDLE, TESTBED_INJECT_OAUTH2_AUTH_SERVICE)
          .add(TEST_APP_COMPONENT, DECLARE_INJECT_OAUTH2_AUTH_SERVICE)
          .and()
        .in(path("src/main/webapp/app/app.component.html"))
          .add(MENU_NEEDLE, indentation.spaces() + "<jhi-login></jhi-login>")
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private String keycloakEnvironment(Indentation indentation) {
    return KEYCLOAK_ENVIRONMENT.indent(indentation.spacesCount());
  }
}
