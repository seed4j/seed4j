package tech.jhipster.lite.generator.client.angular.security.oauth2.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.ANGULAR;

import java.util.regex.Pattern;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.module.domain.replacement.RegexReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public class AngularOauth2ModuleFactory {

  private static final Pattern PROVIDE_HTTP_CLIENT = Pattern.compile("provideHttpClient\\(\\),");
  private static final ElementReplacer EXISTING_PROVIDE_HTTP_CLIENT_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> PROVIDE_HTTP_CLIENT.matcher(contentBeforeReplacement).find(),
    PROVIDE_HTTP_CLIENT
  );

  private static final ElementReplacer ENVIRONMENT_NEEDLE = lineAfterRegex("export const environment *= *\\{");
  private static final String KEYCLOAK_ENVIRONMENT =
    """
    keycloak: {
      url: 'http://localhost:9080',
      realm: 'jhipster',
      client_id: 'web_app',
    },
    """;

  private static final Pattern EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile("(\"allowedCommonJsDependencies\": *\\[\\s*)]");
  private static final ElementReplacer EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN.matcher(contentBeforeReplacement).find(),
    EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final Pattern FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile(
    "(\"allowedCommonJsDependencies\": *\\[[^]]+)]"
  );
  private static final ElementReplacer FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN.matcher(contentBeforeReplacement).find(),
    FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final Pattern FILLED_STANDALONE_PATTERN = Pattern.compile("(imports: *\\[[^]]+)]");
  private static final ElementReplacer FILLED_STANDALONE_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> FILLED_STANDALONE_PATTERN.matcher(contentBeforeReplacement).find(),
    FILLED_STANDALONE_PATTERN
  );

  private static final ElementReplacer MENU_NEEDLE = lineAfterRegex("<span.+id=\\\"menu-space-separator\\\".*></span>");

  private static final String TEST_IMPORTS =
    """
    import { By } from '@angular/platform-browser';
    import LoginComponent from './login/login.component';
    """;
  private static final ElementReplacer TEST_NEEDLE = lineAfterRegex("^\\s+it\\('should have appName',[^}]+\\}\\);");

  private static final String LOGIN_IMPORT =
    """
    import LoginComponent from './login/login.component';
    """;

  private static final String OAUTH2_AUTH_SERVICE_IMPORT =
    """
    import { Oauth2AuthService } from './auth/oauth2-auth.service';
    """;

  private static final ElementReplacer APPNAME_NEEDLE = lineAfterRegex("appName = signal\\(''\\);");

  private static final String INJECT_OAUTH2_AUTH_SERVICE =
    """
      private readonly oauth2AuthService = inject(Oauth2AuthService);\
    """;

  private static final String INIT_AUTHENTICATION =
    """
        this.oauth2AuthService.initAuthentication();\
    """;

  private static final String INJECT_IMPORT =
    """
    import { Component, inject, OnInit, signal } from '@angular/core';
    """;

  private static final ElementReplacer INJECT_NEEDLE = text("import { Component, OnInit, signal } from '@angular/core';");

  private static final String LOGIN_COMPONENT_TEST =
    """

    it('should display login component', () => {
      expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
    });
    """;

  private static final String HTTP_AUTH_INTERCEPTOR_IMPORT =
    """
    import { httpAuthInterceptor } from './app/auth/http-auth.interceptor';
    """;

  private static final JHipsterSource SOURCE = from("client/angular/security/oauth2/src/main/webapp/app");

  private static final JHipsterDestination APP_DESTINATION = to("src/main/webapp/app");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("keycloak-js"), ANGULAR)
        .and()
      .files()
        .batch(SOURCE.append("auth"), APP_DESTINATION.append("auth"))
          .addTemplate("oauth2-auth.service.ts")
          .addTemplate("oauth2-auth.service.spec.ts")
          .addTemplate("http-auth.interceptor.ts")
          .addTemplate("http-auth.interceptor.spec.ts")
          .and()
        .batch(SOURCE.append("login"), APP_DESTINATION.append("login"))
          .addTemplate("login.component.html")
          .addTemplate("login.component.ts")
          .addTemplate("login.component.spec.ts")
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
          .add(FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1, \"base64-js\"]")
          .add(EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1\"base64-js\"]")
          .add(FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1, \"js-sha256\"]")
          .add(EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1\"js-sha256\"]")
          .add(FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1, \"keycloak-js\"]")
          .add(EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1\"keycloak-js\"]")
          .and()
        .in(path("src/main/webapp/main.ts"))
          .add(EXISTING_PROVIDE_HTTP_CLIENT_NEEDLE, "provideHttpClient(withInterceptors([httpAuthInterceptor])),")
          .add(fileStart(), HTTP_AUTH_INTERCEPTOR_IMPORT)
          .and()
        .in(path("src/main/webapp/app/app.component.ts"))
          .add(FILLED_STANDALONE_NEEDLE, "$1, LoginComponent]")
          .add(fileStart(), OAUTH2_AUTH_SERVICE_IMPORT)
          .add(fileStart(), LOGIN_IMPORT)
          .add(INJECT_NEEDLE, INJECT_IMPORT)
          .add(APPNAME_NEEDLE, INJECT_OAUTH2_AUTH_SERVICE)
          .add(lineAfterRegex("this.appName.set\\('" + properties.projectBaseName().name() + "'\\);"), INIT_AUTHENTICATION)
          .and()
        .in(path("src/main/webapp/app/app.component.spec.ts"))
          .add(fileStart(), TEST_IMPORTS)
          .add(TEST_NEEDLE, LOGIN_COMPONENT_TEST.indent(indentation.spacesCount() * 2))
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
