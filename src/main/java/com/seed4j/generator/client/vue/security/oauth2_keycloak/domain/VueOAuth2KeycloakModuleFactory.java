package com.seed4j.generator.client.vue.security.oauth2_keycloak.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public class VueOAuth2KeycloakModuleFactory {

  public static final String REALM_NAME = "keycloakRealmName";
  public static final String DEFAULT_REALM_NAME = "seed4j";
  private static final Pattern NAME_FORMAT = Pattern.compile("^[a-z0-9-]+$");

  private static final Seed4JSource SOURCE = from("client/vue");
  private static final Seed4JSource APP_SOURCE = from("client/vue/security/oauth2_keycloak/webapp/app");
  private static final Seed4JSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");

  private static final Seed4JDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final Seed4JDestination TEST_DESTINATION = to("src/test/webapp");

  private static final String MAIN_TS_IMPORT_NEEDLE = "// seed4j-needle-main-ts-import";
  private static final String MAIN_TS_PROVIDER_NEEDLE = "// seed4j-needle-main-ts-provider";

  private static final String KEYCLOAK_IMPORT = """
  import { provideForAuth } from '@/auth/application/AuthProvider';
  import { KeycloakHttp } from '@/auth/infrastructure/secondary/KeycloakHttp';
  import Keycloak from 'keycloak-js';\
  """;
  private static final String KEYCLOAK_SETUP = """
  const keycloakHttp = new KeycloakHttp(
  %snew Keycloak({
  %surl: 'http://localhost:9080',
  %srealm: '%s',
  %sclientId: 'web_app',
  %s}),
  );

  provideForAuth(keycloakHttp);\
  """;

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();
    String realmName = properties.getOrDefaultString(REALM_NAME, DEFAULT_REALM_NAME);
    Assert.field(REALM_NAME, realmName).notNull().matchesPattern(NAME_FORMAT).maxLength(30);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Vue OAuth2 Keycloak Authentication Components"),
                      DOCUMENTATION_SOURCE.template("vue-oauth2-keycloak-authentication-components.md"))
      .packageJson()
        .addDependency(packageName("keycloak-js"), COMMON)
        .and()
      .files()
        .batch(APP_SOURCE.append("auth"), MAIN_DESTINATION.append("auth"))
          .addTemplate("application/AuthProvider.ts")
          .addTemplate("domain/AuthRepository.ts")
          .addTemplate("domain/AuthenticatedUser.ts")
          .addTemplate("infrastructure/secondary/KeycloakAuthRepository.ts")
          .addTemplate("infrastructure/secondary/KeycloakHttp.ts")
          .and()
        .add(APP_SOURCE.template("test/webapp/unit/auth/application/AuthProvider.spec.ts"), TEST_DESTINATION.append("unit/auth/application/AuthProvider.spec.ts"))
        .batch(APP_SOURCE.append("test/webapp/unit/auth/infrastructure/secondary"), TEST_DESTINATION.append("unit/auth/infrastructure/secondary"))
          .addTemplate("KeycloakAuthRepository.spec.ts")
          .addTemplate("KeycloakHttp.spec.ts")
          .addTemplate("KeycloakHttpStub.ts")
          .addTemplate("KeycloakStub.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/app/main.ts"))
          .add(lineBeforeText(MAIN_TS_IMPORT_NEEDLE),
            KEYCLOAK_IMPORT
          )
          .add(lineBeforeText(MAIN_TS_PROVIDER_NEEDLE),
            KEYCLOAK_SETUP.formatted(indentation.spaces(),
                          indentation.times(2),
                          indentation.times(2),
                          realmName,
                          indentation.times(2),
                          indentation.spaces())
          )
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
