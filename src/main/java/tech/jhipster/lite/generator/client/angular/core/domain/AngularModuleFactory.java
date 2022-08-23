package tech.jhipster.lite.generator.client.angular.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.generator.client.common.domain.ClientsModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class AngularModuleFactory {

  private static final JHipsterSource SOURCE = from("client/angular/core");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return ClientsModulesFactory.clientModuleBuilder(properties)
      .packageJson()
        .addDependency(packageName("@angular/animations"), ANGULAR)
        .addDependency(packageName("@angular/cdk"), ANGULAR)
        .addDependency(packageName("@angular/common"), ANGULAR)
        .addDependency(packageName("@angular/compiler"), ANGULAR)
        .addDependency(packageName("@angular/core"), ANGULAR)
        .addDependency(packageName("@angular/material"), ANGULAR)
        .addDependency(packageName("@angular/forms"), ANGULAR)
        .addDependency(packageName("@angular/platform-browser"), ANGULAR)
        .addDependency(packageName("@angular/platform-browser-dynamic"), ANGULAR)
        .addDependency(packageName("@angular/router"), ANGULAR)
        .addDependency(packageName("rxjs"), ANGULAR)
        .addDependency(packageName("tslib"), ANGULAR)
        .addDependency(packageName("zone.js"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/builder"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/eslint-plugin"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/eslint-plugin-template"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/schematics"), ANGULAR)
        .addDevDependency(packageName("@angular-eslint/template-parser"), ANGULAR)
        .addDevDependency(packageName("@typescript-eslint/eslint-plugin"),ANGULAR)
        .addDevDependency(packageName("@typescript-eslint/parser"),ANGULAR)
        .addDevDependency(packageName("eslint"),COMMON)
        .addDevDependency(packageName("@angular-builders/jest"), ANGULAR)
        .addDevDependency(packageName("@angular-devkit/build-angular"), ANGULAR)
        .addDevDependency(packageName("@angular/cli"), ANGULAR)
        .addDevDependency(packageName("@angular/compiler-cli"), ANGULAR)
        .addDevDependency(packageName("@types/node"), COMMON)
        .addDevDependency(packageName("@types/jest"), COMMON)
        .addDevDependency(packageName("jest"), COMMON)
        .addDevDependency(packageName("jest-environment-jsdom"), ANGULAR)
        .addDevDependency(packageName("ts-jest"), ANGULAR)
        .addDevDependency(packageName("jest-preset-angular"), ANGULAR)
        .addDevDependency(packageName("jest-sonar-reporter"), COMMON)
        .addDevDependency(packageName("typescript"), COMMON)
        .addScript(scriptKey("ng"), scriptCommand("ng"))
        .addScript(scriptKey("start"), scriptCommand("ng serve"))
        .addScript(scriptKey("build"), scriptCommand("ng build --output-path=target/classes/static"))
        .addScript(scriptKey("watch"), scriptCommand("ng build --watch --configuration development"))
        .addScript(scriptKey("test"), scriptCommand("ng test --coverage"))
        .addScript(scriptKey("lint"), scriptCommand("ng lint"))
        .and()
      .files()
        .add(SOURCE.file("jest.conf.js"), to("jest.conf.js"))
        .add(SOURCE.file("angular.json"), to("angular.json"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("tsconfig.app.json"), to("tsconfig.app.json"))
        .add(SOURCE.file("tsconfig.spec.json"), to("tsconfig.spec.json"))
        .add(SOURCE.file("proxy.conf.json"), to("proxy.conf.json"))
        .add(SOURCE.file(".eslintrc.json"), to(".eslintrc.json"))
        .batch(SOURCE.file("src/main/webapp/app"), to("src/main/webapp/app"))
          .addTemplate("app.component.ts")
          .addTemplate("app.component.css")
          .addTemplate("app.component.html")
          .addTemplate("app.component.spec.ts")
          .addTemplate("app.module.ts")
          .addTemplate("app-routing.module.ts")
          .addTemplate("app-routing.module.spec.ts")
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
          .addTemplate("polyfills.ts")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
