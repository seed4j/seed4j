package com.seed4j.generator.client.angular.admin.health.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class AngularHealthModuleFactoryTest {

  private static final AngularHealthModuleFactory factory = new AngularHealthModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, appRouting(), appComponent(), appRoutingSpec(), appMainFile())
      .hasPrefixedFiles("src/main/webapp/app/admin", "admin.routes.ts", "admin.routes.spec.ts")
      .hasPrefixedFiles("src/main/webapp/app/config", "application-config.service.spec.ts", "application-config.service.ts")
      .hasPrefixedFiles(
        "src/main/webapp/app/admin/health",
        "health.css",
        "health.html",
        "health.ts",
        "health.spec.ts",
        "health.model.ts",
        "health.service.spec.ts",
        "health.service.ts"
      )
      .hasPrefixedFiles(
        "src/main/webapp/app/admin/health/modal",
        "health-modal.css",
        "health-modal.html",
        "health-modal.ts",
        "health-modal.spec.ts"
      )
      .hasFile("src/main/webapp/app/app.route.ts")
      .containing(
        """
          {
            path: 'admin',
            loadChildren: () => import('./admin/admin.routes'),
          },
        """
      )
      .and()
      .hasFile("src/main/webapp/app/app.html")
      .containing("<a routerLink=\"admin/health\" mat-menu-item><span>Health</span></a>")
      .and()
      .hasFile("src/main/webapp/app/app.route.spec.ts")
      .containing(
        """
          it('should navigate on admin endpoint', () => {
            router.navigateByUrl('/admin');
          });
        """
      );
  }

  private ModuleFile appRouting() {
    return file("src/test/resources/projects/angular/app.route.ts", "src/main/webapp/app/app.route.ts");
  }

  private ModuleFile appComponent() {
    return file("src/test/resources/projects/angular/app.html", "src/main/webapp/app/app.html");
  }

  private ModuleFile appRoutingSpec() {
    return file("src/test/resources/projects/angular/app.route.spec.ts", "src/main/webapp/app/app.route.spec.ts");
  }

  private static ModuleFile appMainFile() {
    return file("src/test/resources/projects/angular/main.ts", "src/main/webapp/main.ts");
  }
}
