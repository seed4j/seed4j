package tech.jhipster.lite.generator.client.angular.security.jwt.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.regex.Pattern;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.module.domain.replacement.RegexReplacer;
import tech.jhipster.lite.module.domain.replacement.TextNeedleBeforeReplacer;

public class AngularJwtModuleFactory {

  private static final Pattern PROVIDERS_PATTERN = Pattern.compile("(providers: *\\[)");
  private static final ElementReplacer EXISTING_PROVIDERS_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> PROVIDERS_PATTERN.matcher(contentBeforeReplacement).find(),
    PROVIDERS_PATTERN
  );

  private static final TextNeedleBeforeReplacer ROUTE_NEEDLE = lineBeforeText("// jhipster-needle-angular-route");

  private static final String LOGIN_MODULE_ROUTE =
    """
      {
        path: '',
        loadComponent: () => import('./login/login.component'),
        title: 'Login',
      },\
    """;

  private static final String AUTH_INTERCEPTOR_IMPORT = """
      import { AuthInterceptor } from './app/auth/auth.interceptor';
      """;

  private static final JHipsterSource SOURCE = from("client/angular/security/jwt/src/main/webapp/app");

  private static final JHipsterDestination APP_DESTINATION = to("src/main/webapp/app");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();

    //@formatter:off
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
          .addTemplate("login.component.css")
          .addTemplate("login.component.html")
          .addTemplate("login.component.spec.ts")
          .addTemplate("login.component.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/main/webapp/main.ts"))
          .add(EXISTING_PROVIDERS_NEEDLE, "providers: [\n    provideHttpClient(withInterceptors([AuthInterceptor])),")
          .add(fileStart(), AUTH_INTERCEPTOR_IMPORT)
          .and()
        .in(path("src/main/webapp/app/app.route.ts"))
          .add(ROUTE_NEEDLE, LOGIN_MODULE_ROUTE)
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
