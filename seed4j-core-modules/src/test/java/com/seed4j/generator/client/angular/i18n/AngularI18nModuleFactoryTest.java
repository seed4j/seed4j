package com.seed4j.generator.client.angular.i18n;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
      appSpecFile(),
      mainFile(),
      appHtmlFile(),
      translocoTestingFile()
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
      .containing("TranslocoDirective]")
      .and()
      .hasFile("src/main/webapp/app/app.spec.ts")
      .containing("getTranslocoModule()")
      .and()
      .hasFile("src/main/webapp/app/transloco-testing-module.ts");
  }

  @Test
  void shouldWrapAppHtmlAlreadyContainingNgContainerWithBalancedClosingTag() throws IOException {
    Seed4JModule module = factory.buildModule(
      Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build()
    );
    Path customAppHtml = Path.of(TestFileUtils.tmpDirForTest(), "app.html");
    Files.createDirectories(customAppHtml.getParent());
    Files.writeString(
      customAppHtml,
      Files.readString(Path.of("src/test/resources/projects/angular/app.html"))
        + "<ng-container *transloco=\"let other\">extra</ng-container>\n"
    );

    assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      appFile(),
      appSpecFile(),
      mainFile(),
      file(customAppHtml.toString(), "src/main/webapp/app/app.html"),
      translocoTestingFile()
    )
      .hasFile("src/main/webapp/app/app.html")
      .containing("<ng-container *transloco=\"let t\">")
      .containing("<ng-container *transloco=\"let other\">extra</ng-container>\n</ng-container>");
  }

  @Test
  void shouldFailWhenMainTsEnableProdModeImportDoesNotMatch() throws IOException {
    Seed4JModule module = factory.buildModule(
      Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build()
    );
    Path customMainTs = Path.of(TestFileUtils.tmpDirForTest(), "main.ts");
    Files.createDirectories(customMainTs.getParent());
    Files.writeString(
      customMainTs,
      Files.readString(Path.of("src/test/resources/projects/angular/main.ts")).replace(
        "import { enableProdMode } from '@angular/core';",
        "import { ApplicationRef, enableProdMode } from '@angular/core';"
      )
    );
    ModuleFile customMainFile = file(customMainTs.toString(), "src/main/webapp/main.ts");

    assertThatThrownBy(() ->
      assertThatModuleWithFiles(module, packageJsonFile(), appFile(), appSpecFile(), customMainFile, appHtmlFile(), translocoTestingFile())
    )
      .isInstanceOf(GeneratorException.class)
      .hasMessageContaining("Can't find");
  }

  private ModuleFile appFile() {
    return file("src/test/resources/projects/angular/app.ts", "src/main/webapp/app/app.ts");
  }

  private ModuleFile appSpecFile() {
    return file("src/test/resources/projects/angular/app.spec.ts", "src/main/webapp/app/app.spec.ts");
  }

  private ModuleFile appHtmlFile() {
    return file("src/test/resources/projects/angular/app.html", "src/main/webapp/app/app.html");
  }

  private ModuleFile mainFile() {
    return file("src/test/resources/projects/angular/main.ts", "src/main/webapp/main.ts");
  }

  private ModuleFile translocoTestingFile() {
    return file("src/test/resources/projects/angular/transloco-testing-module.ts", "src/main/webapp/app/transloco-testing-module.ts");
  }
}
