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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
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
      .containing(nodeDependency("@angular/build"))
      .containing(nodeDependency("@angular-eslint/builder"))
      .containing(nodeDependency("@angular-eslint/eslint-plugin"))
      .containing(nodeDependency("@angular-eslint/eslint-plugin-template"))
      .containing(nodeDependency("@angular-eslint/schematics"))
      .containing(nodeDependency("@angular-eslint/template-parser"))
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@typescript-eslint/parser"))
      .containing(nodeDependency("eslint"))
      .containing(nodeDependency("npm-run-all2"))
      .containing(nodeScript("ng", "ng"))
      .containing(nodeScript("watch", "npm-run-all --parallel watch:*"))
      .containing(nodeScript("watch:ng", "ng build --watch --configuration development"))
      .containing(nodeScript("start", "ng serve"))
      .containing(nodeScript("build", "npm-run-all build:*"))
      .containing(nodeScript("build:ng", "ng build"))
      .containing(nodeScript("test", "npm run watch:test"))
      .containing(nodeScript("watch:test", "ng test --watch"))
      .containing(nodeScript("test:coverage", "ng test --coverage"))
      .containing(nodeScript("lint", "ng lint"))
      .containing("  \"jestSonar\": {\n    \"reportPath\": \"target/test-results\",\n    \"reportFile\": \"TESTS-results-sonar.xml\"\n  }")
      .and()
      .hasFile(".gitignore")
      .containing(".angular/")
      .containing(".nx/")
      .and()
      .hasFile(".lintstagedrc.cjs")
      .containing(
        """
        module.exports = {
          '{src/**/,}*.ts': ['eslint --fix', 'prettier --write'],
          '*.{md,json,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
        };
        """
      )
      .and()
      .hasFile("src/main/webapp/app/app.component.ts")
      .containing("this.appName.set('jhiTest')")
      .and()
      .hasPrefixedFiles(
        "",
        "jest.conf.js",
        "angular.json",
        "tsconfig.json",
        "tsconfig.app.json",
        "tsconfig.spec.json",
        "proxy.conf.json",
        ".npmrc",
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
      .hasPrefixedFiles("src/main/webapp", "index.html", "main.ts", "styles.css");
  }

  @Test
  void shouldProxyBeUpdatedWhenServerPortPropertyNotDefault() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .put("serverPort", 8081)
      .build();

    JHipsterModule module = factory.buildModule(properties);
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFile())
      .hasFile("proxy.conf.json")
      .containing("\"target\": \"http://localhost:8081\"")
      .notContaining("\"target\": \"http://localhost:8080\"");
  }

  @Test
  void shouldProxyBeDefaultWhenServerPortPropertyMissing() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .build();

    JHipsterModule module = factory.buildModule(properties);
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFile())
      .hasFile("proxy.conf.json")
      .containing("\"target\": \"http://localhost:8080\"");
  }
}
