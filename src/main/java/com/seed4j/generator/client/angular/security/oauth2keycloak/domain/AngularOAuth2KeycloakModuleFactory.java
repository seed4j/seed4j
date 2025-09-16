package com.seed4j.generator.client.angular.security.oauth2keycloak.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.lineAfterRegex;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.regex;
import static com.seed4j.module.domain.Seed4JModule.text;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.replacement.ReplacementCondition.notMatchingRegex;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.replacement.ElementReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public class AngularOAuth2KeycloakModuleFactory {

  private static final Pattern PROVIDE_HTTP_CLIENT = Pattern.compile("provideHttpClient\\(\\),");
  private static final ElementReplacer EXISTING_PROVIDE_HTTP_CLIENT_NEEDLE = regex(
    notMatchingRegex(PROVIDE_HTTP_CLIENT),
    PROVIDE_HTTP_CLIENT
  );

  private static final ElementReplacer ENVIRONMENT_NEEDLE = lineAfterRegex("export const environment *= *\\{");
  private static final String KEYCLOAK_ENVIRONMENT = """
    keycloak: {
      url: 'http://localhost:9080',
      realm: 'seed4j',
      client_id: 'web_app',
    },
    """;

  private static final Pattern EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile("(\"allowedCommonJsDependencies\": *\\[\\s*)]");
  private static final ElementReplacer EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = regex(
    notMatchingRegex(EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN),
    EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final Pattern FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile(
    "(\"allowedCommonJsDependencies\": *\\[[^]]+)]"
  );
  private static final ElementReplacer FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = regex(
    notMatchingRegex(FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN),
    FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final Pattern FILLED_STANDALONE_PATTERN = Pattern.compile("(imports: *\\[[^]]+)]");
  private static final ElementReplacer FILLED_STANDALONE_NEEDLE = regex(
    notMatchingRegex(FILLED_STANDALONE_PATTERN),
    FILLED_STANDALONE_PATTERN
  );

  private static final ElementReplacer MENU_NEEDLE = lineAfterRegex("<span.+id=\\\"menu-space-separator\\\".*></span>");

  private static final String LOGIN_IMPORT = "import Login from './login/login';";

  private static final String OAUTH2_AUTH_SERVICE_IMPORT = """
    import { Oauth2AuthService } from './auth/oauth2-auth.service';
    """;

  private static final ElementReplacer APP_NAME_NEEDLE = lineAfterRegex("appName = signal\\(''\\);");

  private static final String INJECT_OAUTH2_AUTH_SERVICE = """
      private readonly oauth2AuthService = inject(Oauth2AuthService);\
    """;

  private static final String INIT_AUTHENTICATION = """
        this.oauth2AuthService.initAuthentication();\
    """;

  private static final String INJECT_IMPORT = """
    import { Component, inject, OnInit, signal } from '@angular/core';
    """;

  private static final ElementReplacer INJECT_NEEDLE = text("import { Component, OnInit, signal } from '@angular/core';");

  private static final String HTTP_AUTH_INTERCEPTOR_IMPORT = "import { httpAuthInterceptor } from './app/auth/http-auth.interceptor';";

  private static final Seed4JSource SOURCE = from("client/angular/security/oauth2keycloak/src/main/webapp/app");

  private static final Seed4JDestination APP_DESTINATION = to("src/main/webapp/app");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("keycloak-js"), COMMON)
        .and()
      .files()
        .batch(SOURCE.append("auth"), APP_DESTINATION.append("auth"))
          .addTemplate("oauth2-auth.service.ts")
          .addTemplate("oauth2-auth.service.spec.ts")
          .addTemplate("http-auth.interceptor.ts")
          .addTemplate("http-auth.interceptor.spec.ts")
          .and()
        .batch(SOURCE.append("login"), APP_DESTINATION.append("login"))
          .addTemplate("login.html")
          .addTemplate("login.ts")
          .addTemplate("login.spec.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/environments/environment.ts"))
          .add(ENVIRONMENT_NEEDLE, keycloakEnvironment(indentation))
          .and()
        .in(path("src/main/webapp/environments/environment.local.ts"))
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
          .add(lineAfterRegex("from '@angular/router';"), HTTP_AUTH_INTERCEPTOR_IMPORT)
          .and()
        .in(path("src/main/webapp/app/app.ts"))
          .add(FILLED_STANDALONE_NEEDLE, "$1, Login]")
          .add(lineAfterRegex("from '@angular/core';"), OAUTH2_AUTH_SERVICE_IMPORT)
          .add(lineAfterRegex("from './auth/oauth2-auth.service';"), LOGIN_IMPORT)
          .add(INJECT_NEEDLE, INJECT_IMPORT)
          .add(APP_NAME_NEEDLE, INJECT_OAUTH2_AUTH_SERVICE)
          .add(lineAfterRegex("this.appName.set\\('" + properties.projectBaseName().name() + "'\\);"), INIT_AUTHENTICATION)
          .and()
        .in(path("src/main/webapp/app/app.html"))
          .add(MENU_NEEDLE, indentation.spaces() + "<seed-login />")
          .and()
        .and()
      .build();
    // @formatter:on
  }

  private String keycloakEnvironment(Indentation indentation) {
    String keycloakEnvironmentIndent = KEYCLOAK_ENVIRONMENT.indent(indentation.spacesCount());
    return keycloakEnvironmentIndent.substring(0, keycloakEnvironmentIndent.length() - 1);
  }
}
