package com.seed4j.generator.client.angular.admin.health.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class AngularHealthModuleFactory {

  private static final JHipsterSource SOURCE = from("client/angular/admin/src/main/webapp/app");

  private static final JHipsterProjectFilePath APP_PATH = path("src/main/webapp/app");
  private static final JHipsterDestination APP_DESTINATION = to(APP_PATH.get());

  private static final String ADMIN_NAVIGATION_TEST = """
      it('should navigate on admin endpoint', () => {
        router.navigateByUrl('/admin');
      });\
    """;

  private static final String HEALTH_LINK = "    <a routerLink=\"admin/health\" mat-menu-item><span>Health</span></a>";

  private static final String ADMIN_ROUTING = """
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.routes'),
      },\
    """;

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("admin"), APP_DESTINATION.append("admin"))
          .addTemplate("admin.routes.ts")
          .addTemplate("admin.routes.spec.ts")
          .and()
        .batch(SOURCE.append("config"), APP_DESTINATION.append("config"))
          .addTemplate("application-config.service.spec.ts")
          .addTemplate("application-config.service.ts")
          .and()
        .batch(SOURCE.append("admin/health"), APP_DESTINATION.append("admin/health"))
          .addTemplate("health.css")
          .addTemplate("health.html")
          .addTemplate("health.ts")
          .addTemplate("health.spec.ts")
          .addTemplate("health.model.ts")
          .addTemplate("health.service.spec.ts")
          .addTemplate("health.service.ts")
          .and()
        .batch(SOURCE.append("admin/health/modal"), APP_DESTINATION.append("admin/health/modal"))
          .addTemplate("health-modal.css")
          .addTemplate("health-modal.html")
          .addTemplate("health-modal.ts")
          .addTemplate("health-modal.spec.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(APP_PATH.append("app.route.ts"))
          .add(lineBeforeText("// seed4j-needle-angular-route"), ADMIN_ROUTING)
          .and()
        .in(APP_PATH.append("app.html"))
          .add(lineBeforeText("<!-- seed4j-needle-angular-menu -->"), HEALTH_LINK)
          .and()
        .in(APP_PATH.append("app.route.spec.ts"))
          .add(lineBeforeText("// seed4j-needle-angular-menu"), ADMIN_NAVIGATION_TEST)
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
