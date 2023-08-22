package tech.jhipster.lite.generator.client.angular.core.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class AngularModuleFactoryTest {

  private static final AngularModuleFactory factory = new AngularModuleFactory();

  @Test
  void shouldCreateAngularModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFile())
      .hasFile("package.json")
      .containing(nodeDependency("zone.js"))
      .containing(nodeDependency("tslib"))
      .containing(nodeDependency("rxjs"))
      .containing(nodeDependency("@angular/router"))
      .containing(nodeDependency("@angular/platform-browser-dynamic"))
      .containing(nodeDependency("@angular/platform-browser"))
      .containing(nodeDependency("@angular/forms"))
      .containing(nodeDependency("@angular/material"))
      .containing(nodeDependency("@angular/core"))
      .containing(nodeDependency("@angular/compiler"))
      .containing(nodeDependency("@angular/common"))
      .containing(nodeDependency("@angular/cdk"))
      .containing(nodeDependency("@angular/animations"))
      .containing(nodeDependency("@angular-devkit/build-angular"))
      .containing(nodeDependency("@angular-eslint/builder"))
      .containing(nodeDependency("@angular-eslint/eslint-plugin"))
      .containing(nodeDependency("@angular-eslint/eslint-plugin-template"))
      .containing(nodeDependency("@angular-eslint/schematics"))
      .containing(nodeDependency("@angular-eslint/template-parser"))
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@typescript-eslint/parser"))
      .containing(nodeDependency("eslint"))
      .containing("\"ng\": \"ng\"")
      .containing("\"watch\": \"ng build --watch --configuration development\"")
      .containing("\"start\": \"ng serve\"")
      .containing("\"build\": \"ng build --output-path=target/classes/static\"")
      .containing("\"test\": \"ng test --coverage\"")
      .containing("\"lint\": \"ng lint\"")
      .containing("  \"jestSonar\": {\n    \"reportPath\": \"target/test-results\",\n    \"reportFile\": \"TESTS-results-sonar.xml\"\n  },")
      .and()
      .hasFile(".lintstagedrc.js")
      .containing(
        """
        module.exports = {
          '{src/**/,}*.{js,ts,tsx,vue}': ['eslint --fix'],
          '{src/**/,}*.{md,json,yml,html,css,scss,java,xml}': ['prettier --write'],
        };
        """
      )
      .and()
      .hasFile("src/main/webapp/app/app.component.ts")
      .containing("this.appName = 'jhiTest'")
      .and()
      .hasPrefixedFiles(
        "",
        "jest.conf.js",
        "angular.json",
        "tsconfig.json",
        "tsconfig.app.json",
        "tsconfig.spec.json",
        "proxy.conf.json",
        ".eslintignore",
        ".eslintrc.json"
      )
      .hasPrefixedFiles(
        "src/main/webapp/app",
        "app.component.css",
        "app.component.ts",
        "app.component.html",
        "app.component.spec.ts",
        "app.route.spec.ts",
        "app.route.ts"
      )
      .hasPrefixedFiles("src/main/webapp/content/images", "JHipster-Lite-neon-red.png", "AngularLogo.svg")
      .hasPrefixedFiles(
        "src/main/webapp/environments",
        "environment.ts",
        "environment.prod.ts",
        "environment.prod.spec.ts",
        "environment.spec.ts"
      )
      .hasPrefixedFiles("src/main/webapp", "index.html", "main.ts", "polyfills.ts", "styles.css");
  }
}
