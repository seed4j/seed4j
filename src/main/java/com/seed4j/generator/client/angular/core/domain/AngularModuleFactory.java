package com.seed4j.generator.client.angular.core.domain;

import static com.seed4j.module.domain.JHipsterModule.LINE_BREAK;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.packageName;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.preCommitCommands;
import static com.seed4j.module.domain.JHipsterModule.runScriptCommandWith;
import static com.seed4j.module.domain.JHipsterModule.scriptCommand;
import static com.seed4j.module.domain.JHipsterModule.scriptKey;
import static com.seed4j.module.domain.JHipsterModule.stagedFilesFilter;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.nodejs.JHLiteNodePackagesVersionSource.ANGULAR;
import static com.seed4j.module.domain.nodejs.JHLiteNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.packagejson.PackageName;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class AngularModuleFactory {

  private static final JHipsterSource SOURCE = from("client/angular/core");

  private static final String ENGINES_NEEDLE = "  \"engines\":";
  private static final PackageName ANGULAR_CORE_PACKAGE = packageName("@angular/core");
  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  public AngularModuleFactory(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.nodeLazyPackagesInstaller = nodeLazyPackagesInstaller;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .preCommitActions(stagedFilesFilter("{src/**/,}*.ts"), preCommitCommands("eslint --fix", "prettier --write"))
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
        .addDevDependency(packageName("@typescript-eslint/eslint-plugin"), COMMON)
        .addDevDependency(packageName("@typescript-eslint/parser"), COMMON)
        .addDevDependency(packageName("eslint"), COMMON)
        .addDevDependency(packageName("@angular-builders/jest"), ANGULAR)
        .addDevDependency(packageName("@angular/build"), ANGULAR)
        .addDevDependency(packageName("@angular/cli"), ANGULAR)
        .addDevDependency(packageName("@angular/compiler-cli"), ANGULAR, ANGULAR_CORE_PACKAGE)
        .addDevDependency(packageName("@types/node"), COMMON)
        .addDevDependency(packageName("@types/jest"), COMMON)
        .addDevDependency(packageName("angular-eslint"), ANGULAR)
        .addDevDependency(packageName("globals"), COMMON)
        .addDevDependency(packageName("jest"), COMMON)
        .addDevDependency(packageName("jest-environment-jsdom"), ANGULAR)
        .addDevDependency(packageName("ts-jest"), COMMON)
        .addDevDependency(packageName("jest-preset-angular"), ANGULAR)
        .addDevDependency(packageName("jest-sonar-reporter"), ANGULAR)
        .addDevDependency(packageName("typescript"), ANGULAR)
        .addDevDependency(packageName("typescript-eslint"), COMMON)
        .addDevDependency(packageName("npm-run-all2"), COMMON)
        .addScript(scriptKey("dev"), scriptCommand("npm-run-all --parallel dev:*"))
        .addScript(scriptKey("dev:ng"), scriptCommand("ng serve"))
        .addScript(scriptKey("ng"), scriptCommand("ng"))
        .addScript(scriptKey("start"), scriptCommand("ng serve"))
        .addScript(scriptKey("build"), scriptCommand("npm-run-all build:*"))
        .addScript(scriptKey("build:ng"), scriptCommand("ng build"))
        .addScript(scriptKey("watch"), scriptCommand("npm-run-all --parallel watch:*"))
        .addScript(scriptKey("watch:ng"), scriptCommand("ng build --watch --configuration development"))
        .addScript(scriptKey("test"), runScriptCommandWith(properties.nodePackageManager(), "watch:test"))
        .addScript(scriptKey("test:coverage"), scriptCommand("ng test --coverage"))
        .addScript(scriptKey("watch:test"), scriptCommand("ng test --watch"))
        .addScript(scriptKey("lint"), scriptCommand("eslint ."))
        .and()
      .postActions()
        .add(context -> nodeLazyPackagesInstaller.runInstallIn(context.projectFolder(), properties.nodePackageManager()))
        .and()
      .files()
        .add(SOURCE.template("angular.json"), to("angular.json"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("tsconfig.app.json"), to("tsconfig.app.json"))
        .batch(SOURCE, to("."))
          .addTemplate("eslint.config.mjs")
          .addTemplate("proxy.conf.json")
          .addTemplate("jest.conf.mjs")
          .addTemplate("tsconfig.spec.json")
          .and()
        .batch(SOURCE.file("src/main/webapp/app"), to("src/main/webapp/app"))
          .addTemplate("app.ts")
          .addTemplate("app.css")
          .addTemplate("app.html")
          .addTemplate("app.spec.ts")
          .addTemplate("app.route.ts")
          .addTemplate("app.route.spec.ts")
          .and()
        .batch(SOURCE.file("src/main/webapp/content/images"), to("src/main/webapp/content/images"))
          .addFile("JHipster-Lite-neon-red.png")
          .addFile("AngularLogo.svg")
          .and()
        .batch(SOURCE.file("src/main/webapp/environments"), to("src/main/webapp/environments"))
          .addTemplate("environment.local.ts")
          .addTemplate("environment.local.spec.ts")
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
    // @formatter:on
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
