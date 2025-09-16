package com.seed4j.generator.client.vue.security.jwt.domain;

import static com.seed4j.module.domain.Seed4JModule.*;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

public class VueJwtModuleFactory {

  private static final Seed4JSource SOURCE = from("client/vue");
  private static final Seed4JSource APP_SOURCE = from("client/vue/security/jwt/webapp/app");
  private static final Seed4JSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");

  private static final Seed4JDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final Seed4JDestination TEST_DESTINATION = to("src/test/webapp");

  private static final String MAIN_TS_IMPORT_NEEDLE = "// seed4j-needle-main-ts-import";
  private static final String MAIN_TS_PROVIDER_NEEDLE = "// seed4j-needle-main-ts-provider";

  private static final String JWT_IMPORT = """
    import { provideForAuth } from '@/auth/application/AuthProvider';
    import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
    import axios from 'axios';
    """;

  private static final String JWT_SETUP = """
    const axiosInstance = axios.create({ baseURL: 'http://localhost:8080/' });
    const axiosHttp = new AxiosHttp(axiosInstance);
    provideForAuth(axiosHttp);
    """;

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("springBootJwtBasicAuthModule", Seed4JCoreModuleSlug.SPRING_BOOT_JWT_BASIC_AUTH.get())
        .and()
      .documentation(documentationTitle("Vue JWT Authentication Components"),
        DOCUMENTATION_SOURCE.template("vue-jwt-authentication-components.md"))
      .files()
        .batch(APP_SOURCE.append("auth"), MAIN_DESTINATION.append("auth"))
          .addTemplate("application/AuthProvider.ts")
          .addTemplate("domain/AuthRepository.ts")
          .addTemplate("domain/AuthenticatedUser.ts")
          .addTemplate("domain/Authentication.ts")
          .addTemplate("domain/LoginCredentials.ts")
          .addTemplate("infrastructure/secondary/JwtAuthRepository.ts")
          .addTemplate("infrastructure/secondary/RestAuthentication.ts")
          .addTemplate("infrastructure/secondary/RestLoginCredentials.ts")
          .and()
        .add(APP_SOURCE.template("test/webapp/unit/auth/application/AuthProvider.spec.ts"), TEST_DESTINATION.append("unit/auth/application/AuthProvider.spec.ts"))
        .add(APP_SOURCE.template("test/webapp/unit/auth/infrastructure/secondary/JwtAuthRepository.spec.ts"), TEST_DESTINATION.append("unit/auth/infrastructure/secondary/JwtAuthRepository.spec.ts"))
        .add(APP_SOURCE.template("test/webapp/unit/shared/http/infrastructure/secondary/AxiosHttpStub.ts"), TEST_DESTINATION.append("unit/shared/http/infrastructure/secondary/AxiosHttpStub.ts"))
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/app/main.ts"))
          .add(lineBeforeText(MAIN_TS_IMPORT_NEEDLE), JWT_IMPORT)
          .add(lineBeforeText(MAIN_TS_PROVIDER_NEEDLE), JWT_SETUP)
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
