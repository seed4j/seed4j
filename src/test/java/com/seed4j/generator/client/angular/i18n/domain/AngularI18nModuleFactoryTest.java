package com.seed4j.generator.client.angular.i18n.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions;
import org.junit.jupiter.api.Test;

@UnitTest
class AngularI18nModuleFactoryTest {

  private static final AngularI18nModuleFactory factory = new AngularI18nModuleFactory();

  @Test
  void shouldBuildI18nModule() {
    Seed4JModule module = factory.buildModule(
      Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build()
    );

    Seed4JModulesAssertions.Seed4JModuleAsserter asserter = assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      appFile(),
      mainFile(),
      appHtmlFile()
    );
    asserter
      .hasFile("package.json")
      .containing(nodeDependency("@jsverse/transloco"))
      .and()
      .hasFile("src/main/webapp/transloco-loader.ts")
      .and()
      .hasFile("src/main/webapp/main.ts")
      .containing("import { provideTransloco } from '@jsverse/transloco';")
      .containing("provideTransloco({")
      .containing("!isDevMode()")
      .containingPattern("import\\s*\\{[^}]*isDevMode[^}]*\\}\\s*from\\s*['\"]@angular/core['\"];")
      .and()
      .hasFile("src/main/webapp/app/app.html")
      .containing("<h2>{{ t('home.translationEnabled') }}</h2>")
      .containing("<ng-container *transloco=\"let t\">")
      .containing("</ng-container>")
      .and()
      .hasFile("src/main/webapp/app/app.ts")
      .containing("import { TranslocoDirective } from '@jsverse/transloco';")
      .containing("TranslocoDirective]");
  }

  private ModuleFile appFile() {
    return file("src/test/resources/projects/angular/app.ts", "src/main/webapp/app/app.ts");
  }

  private ModuleFile appHtmlFile() {
    return file("src/test/resources/projects/angular/app.html", "src/main/webapp/app/app.html");
  }

  private ModuleFile mainFile() {
    return file("src/test/resources/projects/angular/main.ts", "src/main/webapp/main.ts");
  }
}
