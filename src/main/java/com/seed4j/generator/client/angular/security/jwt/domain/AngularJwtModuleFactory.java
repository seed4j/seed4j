package com.seed4j.generator.client.angular.security.jwt.domain;

import static com.seed4j.module.domain.Seed4JModule.fileStart;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.regex;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.replacement.ReplacementCondition.notMatchingRegex;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.replacement.ElementReplacer;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public class AngularJwtModuleFactory {

  private static final Pattern PROVIDE_HTTP_CLIENT_PATTERN = Pattern.compile("provideHttpClient\\(\\),");
  private static final ElementReplacer EXISTING_PROVIDE_HTTP_CLIENT_NEEDLE = regex(
    notMatchingRegex(PROVIDE_HTTP_CLIENT_PATTERN),
    PROVIDE_HTTP_CLIENT_PATTERN
  );

  private static final TextNeedleBeforeReplacer ROUTE_NEEDLE = lineBeforeText("// seed4j-needle-angular-route");

  private static final String LOGIN_MODULE_ROUTE = """
      {
        path: '',
        loadComponent: () => import('./login/login'),
      },\
    """;

  private static final String AUTH_INTERCEPTOR_IMPORT = """
    import { authInterceptor } from './app/auth/auth.interceptor';
    """;

  private static final Seed4JSource SOURCE = from("client/angular/security/jwt/src/main/webapp/app");

  private static final Seed4JDestination APP_DESTINATION = to("src/main/webapp/app");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("auth"), APP_DESTINATION.append("auth"))
          .addTemplate("account.model.ts")
          .addTemplate("account.model.spec.ts")
          .addTemplate("account.service.ts")
          .addTemplate("account.service.spec.ts")
          .addTemplate("auth.interceptor.ts")
          .addTemplate("auth.interceptor.spec.ts")
          .addTemplate("auth-jwt.service.ts")
          .addTemplate("auth-jwt.service.spec.ts")
          .and()
        .batch(SOURCE.append("login"), APP_DESTINATION.append("login"))
          .addTemplate("login.service.ts")
          .addTemplate("login.service.spec.ts")
          .addTemplate("login.model.ts")
          .addTemplate("login.css")
          .addTemplate("login.html")
          .addTemplate("login.spec.ts")
          .addTemplate("login.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/main.ts"))
          .add(EXISTING_PROVIDE_HTTP_CLIENT_NEEDLE, "provideHttpClient(withInterceptors([authInterceptor])),")
          .add(fileStart(), AUTH_INTERCEPTOR_IMPORT)
          .and()
        .in(path("src/main/webapp/app/app.route.ts"))
          .add(ROUTE_NEEDLE, LOGIN_MODULE_ROUTE)
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
