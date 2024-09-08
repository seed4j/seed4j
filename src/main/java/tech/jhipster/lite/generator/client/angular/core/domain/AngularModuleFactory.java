package tech.jhipster.lite.generator.client.angular.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.ANGULAR;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.COMMON;

import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.packagejson.PackageName;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class AngularModuleFactory {

  private static final JHipsterSource SOURCE = from("client/angular/core");

  private static final JHipsterSource SOURCE_COMMON = from("client/common");

  private static final String ENGINES_NEEDLE = "  \"engines\":";
  private static final PackageName ANGULAR_CORE_PACKAGE = packageName("@angular/core");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .optionalReplacements()
        .in(path(".lintstagedrc.cjs"))
          .add(lineBeforeRegex("\\['prettier --write'\\]"), "  '{src/**/,}*.ts': ['eslint --fix', 'prettier --write'],")
          .and()
        .and()
      .gitIgnore()
        .comment("Angular")
        .pattern(".angular/")
        .pattern(".nx/")
        .and()
      .packageJson()
        .addDependency(packageName("@angular/animations"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDependency(packageName("@angular/cdk"), ANGULAR)
        .addDependency(packageName("@angular/common"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDependency(packageName("@angular/compiler"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDependency(ANGULAR_CORE_PACKAGE, ANGULAR)
        .addDependency(packageName("@angular/material"), ANGULAR)
        .addDependency(packageName("@angular/forms"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDependency(packageName("@angular/platform-browser"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDependency(packageName("@angular/platform-browser-dynamic"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDependency(packageName("@angular/router"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDependency(packageName("rxjs"), ANGULAR)
        .addDependency(packageName("tslib"), ANGULAR)
        .addDependency(packageName("zone.js"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/builder"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/eslint-plugin"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/eslint-plugin-template"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/schematics"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/template-parser"), ANGULAR)
        .addDevDependency(packageName("@typescript-eslint/eslint-plugin"), COMMON)
        .addDevDependency(packageName("@typescript-eslint/parser"), COMMON)
        .addDevDependency(packageName("eslint"), COMMON)
        .addDevDependency(packageName("@angular-builders/jest"), ANGULAR)
        .addDevDependency(packageName("@angular/build"), ANGULAR)
        .addDevDependency(packageName("@angular/cli"), ANGULAR)
        .addDevDependency(packageName("@angular/compiler-cli"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDevDependency(packageName("@types/node"), COMMON)
        .addDevDependency(packageName("@types/jest"), COMMON)
        .addDevDependency(packageName("jest"), COMMON)
        .addDevDependency(packageName("jest-environment-jsdom"), ANGULAR)
        .addDevDependency(packageName("ts-jest"), COMMON)
        .addDevDependency(packageName("jest-preset-angular"), ANGULAR)
        .addDevDependency(packageName("jest-sonar-reporter"), ANGULAR)
        .addDevDependency(packageName("typescript"), ANGULAR)
        .addDevDependency(packageName("npm-run-all2"), COMMON)
        .addScript(scriptKey("ng"), scriptCommand("ng"))
        .addScript(scriptKey("start"), scriptCommand("ng serve"))
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:ng"), scriptCommand("ng build"))
        .addScript(scriptKey("watch"), scriptCommand("npm-run-all --parallel watch:*"))
        .addScript(scriptKey("watch:ng"), scriptCommand("ng build --watch --configuration development"))
        .addScript(scriptKey("test"), scriptCommand("npm run watch:test"))
        .addScript(scriptKey("test:coverage"), scriptCommand("ng test --coverage"))
        .addScript(scriptKey("watch:test"), scriptCommand("ng test --watch"))
        .addScript(scriptKey("lint"), scriptCommand("ng lint"))
        .and()
      .files()
        .add(SOURCE.template("angular.json"), to("angular.json"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("tsconfig.app.json"), to("tsconfig.app.json"))
        .add(SOURCE.file(".eslintrc.json"), to(".eslintrc.json"))
        .batch(SOURCE_COMMON, to("."))
          .addFile(".eslintignore")
          .addFile(".npmrc")
          .and()
        .batch(SOURCE, to("."))
          .addTemplate("proxy.conf.json")
          .addTemplate("jest.conf.js")
          .addTemplate("tsconfig.spec.json")
          .and()
        .batch(SOURCE.file("src/main/webapp/app"), to("src/main/webapp/app"))
          .addTemplate("app.component.ts")
          .addTemplate("app.component.css")
          .addTemplate("app.component.html")
          .addTemplate("app.component.spec.ts")
          .addTemplate("app.route.ts")
          .addTemplate("app.route.spec.ts")
          .and()
        .batch(SOURCE.file("src/main/webapp/content/images"), to("src/main/webapp/content/images"))
          .addFile("JHipster-Lite-neon-red.png")
          .addFile("AngularLogo.svg")
          .and()
        .batch(SOURCE.file("src/main/webapp/environments"), to("src/main/webapp/environments"))
          .addTemplate("environment.prod.ts")
          .addTemplate("environment.prod.spec.ts")
          .addTemplate("environment.ts")
          .addTemplate("environment.spec.ts")
          .and()
        .batch(SOURCE.file("src/main/webapp"), to("src/main/webapp"))
          .addTemplate("index.html")
          .addTemplate("styles.css")
          .addTemplate("main.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("package.json"))
          .add(lineBeforeText(ENGINES_NEEDLE), jestSonar(properties.indentation()))
        .and()
      .and()
      .build();
    //@formatter:on
  }

  private static String jestSonar(Indentation indentation) {
    return new StringBuilder()
      .append(indentation.spaces())
      .append("\"jestSonar\": {")
      .append(LINE_BREAK)
      .append(indentation.times(2))
      .append("\"reportPath\": \"{{projectBuildDirectory}}/test-results\",")
      .append(LINE_BREAK)
      .append(indentation.times(2))
      .append("\"reportFile\": \"TESTS-results-sonar.xml\"")
      .append(LINE_BREAK)
      .append(indentation.spaces())
      .append("},")
      .toString();
  }
}
