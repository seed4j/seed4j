package com.seed4j.generator.client.angular.core.domain;

import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.lintStagedConfigFileWithPrettier;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.packageJsonFile;
import static org.mockito.Mockito.verify;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AngularModuleFactoryTest {

  @InjectMocks
  private AngularModuleFactory factory;

  @Mock
  private NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier())
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
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@typescript-eslint/parser"))
      .containing(nodeDependency("angular-eslint"))
      .containing(nodeDependency("eslint"))
      .containing(nodeDependency("globals"))
      .containing(nodeDependency("typescript-eslint"))
      .containing(nodeDependency("npm-run-all2"))
      .containing(nodeDependency("vite-tsconfig-paths"))
      .containing(nodeDependency("@vitest/coverage-istanbul"))
      .containing(nodeDependency("vitest-sonar-reporter"))
      .containing(nodeDependency("@analogjs/vite-plugin-angular"))
      .containing(nodeDependency("@analogjs/vitest-angular"))
      .containing(nodeDependency("jsdom"))
      .containing(nodeScript("dev", "npm-run-all --parallel dev:*"))
      .containing(nodeScript("dev:ng", "ng serve"))
      .containing(nodeScript("ng", "ng"))
      .containing(nodeScript("watch", "npm-run-all --parallel watch:*"))
      .containing(nodeScript("watch:ng", "ng build --watch --configuration development"))
      .containing(nodeScript("start", "ng serve"))
      .containing(nodeScript("build", "npm-run-all build:*"))
      .containing(nodeScript("build:ng", "ng build"))
      .containing(nodeScript("test", "npm run watch:test"))
      .containing(nodeScript("watch:test", "ng test --watch"))
      .containing(nodeScript("test:coverage", "ng test --coverage"))
      .containing(nodeScript("lint", "eslint ."))
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
      .hasFile("src/main/webapp/app/app.ts")
      .containing("this.appName.set('growth')")
      .and()
      .hasPrefixedFiles(
        "",
        "angular.json",
        "tsconfig.json",
        "tsconfig.app.json",
        "tsconfig.spec.json",
        "proxy.conf.json",
        "eslint.config.mjs",
        "test-setup.ts",
        "vitest.config.ts"
      )
      .hasPrefixedFiles("src/main/webapp/app", "app.css", "app.ts", "app.html", "app.spec.ts", "app.route.spec.ts", "app.route.ts")
      .hasPrefixedFiles("src/main/webapp/content/images", "seed4j_logo-name.png", "AngularLogo.svg")
      .hasPrefixedFiles(
        "src/main/webapp/environments",
        "environment.ts",
        "environment.local.ts",
        "environment.local.spec.ts",
        "environment.spec.ts"
      )
      .hasPrefixedFiles("src/main/webapp", "index.html", "main.ts", "styles.css");
    verify(nodeLazyPackagesInstaller).runInstallIn(properties.projectFolder(), properties.nodePackageManager());
  }

  @Test
  void shouldBuildModuleWithPnpm() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .nodePackageManager(PNPM)
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier())
      .hasFile("package.json")
      .containing(nodeScript("test", "pnpm run watch:test"));
  }

  @Test
  void shouldProxyBeUpdatedWhenServerPortPropertyNotDefault() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("growth")
      .put("serverPort", 8081)
      .build();

    Seed4JModule module = factory.buildModule(properties);
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier())
      .hasFile("proxy.conf.json")
      .containing("\"target\": \"http://localhost:8081\"")
      .notContaining("\"target\": \"http://localhost:8080\"");
  }

  @Test
  void shouldProxyBeDefaultWhenServerPortPropertyMissing() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier())
      .hasFile("proxy.conf.json")
      .containing("\"target\": \"http://localhost:8080\"");
  }
}
