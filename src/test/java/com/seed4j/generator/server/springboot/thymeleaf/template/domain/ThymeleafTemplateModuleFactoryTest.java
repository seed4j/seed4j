package com.seed4j.generator.server.springboot.thymeleaf.template.domain;

import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.packageJsonFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class ThymeleafTemplateModuleFactoryTest {

  private static final ThymeleafTemplateModuleFactory factory = new ThymeleafTemplateModuleFactory();

  @Test
  void shouldBuildThymeleafTemplateModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFiles("documentation/thymeleaf.md")
      .hasFile("package.json")
        .containing(nodeDependency("@babel/cli"))
        .containing(nodeDependency("autoprefixer"))
        .containing(nodeDependency("browser-sync"))
        .containing(nodeDependency("cssnano"))
        .containing(nodeDependency("mkdirp"))
        .containing(nodeDependency("npm-run-all2"))
        .containing(nodeDependency("onchange"))
        .containing(nodeDependency("path-exists-cli"))
        .containing(nodeDependency("postcss"))
        .containing(nodeDependency("postcss-cli"))
        .containing(nodeDependency("recursive-copy-cli"))
        .containing(nodeScript("build", "npm-run-all --parallel build:*"))
        .containing(nodeScript("build:html", "recursive-copy 'src/main/resources/templates' target/classes/templates -w"))
        .containing(nodeScript("build:css", "mkdirp target/classes/static/css && postcss src/main/resources/static/css/*.css -d target/classes/static/css"))
        .containing(nodeScript("build:js", "path-exists src/main/resources/static/js && (mkdirp target/classes/static/js && babel src/main/resources/static/js/ --out-dir target/classes/static/js/) || echo 'No src/main/resources/static/js directory found.'"))
        .containing(nodeScript("build:svg", "path-exists src/main/resources/static/svg && recursive-copy 'src/main/resources/static/svg' target/classes/static/svg -w -f '**/*.svg' || echo 'No src/main/resources/static/svg directory found.'"))
        .containing(nodeScript("build-prod", "NODE_ENV='production' npm-run-all --parallel build-prod:*"))
        .containing(nodeScript("build-prod:html", "npm run build:html"))
        .containing(nodeScript("build-prod:css", "npm run build:css"))
        .containing(nodeScript("build-prod:js", "path-exists src/main/resources/static/js && (mkdirp target/classes/static/js && babel src/main/resources/static/js/ --minified --out-dir target/classes/static/js/) || echo 'No src/main/resources/static/js directory found.'"))
        .containing(nodeScript("build-prod:svg", "npm run build:svg"))
        .containing(nodeScript("watch:html", "onchange 'src/main/resources/templates/**/*.html' -- npm run build:html"))
        .containing(nodeScript("watch:css", "onchange 'src/main/resources/static/css/**/*.css' -- npm run build:css"))
        .containing(nodeScript("watch:js", "onchange 'src/main/resources/static/js/**/*.js' -- npm run build:js"))
        .containing(nodeScript("watch:svg", "onchange 'src/main/resources/static/svg/**/*.svg' -- npm run build:svg"))
        .containing(nodeScript("watch", "npm-run-all --parallel watch:*"))
        .containing(nodeScript("watch:serve", "browser-sync start --proxy localhost:8080 --files 'target/classes/templates' 'target/classes/static'"))
        .and()
      .hasFiles("src/main/resources/templates/index.html")
      .hasFiles("src/main/resources/templates/layout/main.html")
      .hasFiles("src/main/resources/static/css/application.css")
      .hasFiles("postcss.config.js");
    // @formatter:on
  }

  @Test
  void shouldBuildThymeleafTemplateModuleWithPnpm() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .nodePackageManager(PNPM)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
        .containing(nodeScript("build", "npm-run-all --parallel build:*"))
        .containing(nodeScript("build-prod:html", "pnpm run build:html"))
        .containing(nodeScript("build-prod:css", "pnpm run build:css"))
        .containing(nodeScript("build-prod:svg", "pnpm run build:svg"));
    // @formatter:on
  }

  @Test
  void shouldBuildTailwindcssModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhipster")
      .build();

    JHipsterModule module = factory.buildTailwindcssModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(
        module,
        packageJsonFile(),
        appPostcssFile(),
        appCssFile(),
        appIndexFile()
    )
      .hasFile("package.json")
        .containing(nodeDependency("tailwindcss"))
        .containing(
          """
          "watch:html": "onchange 'src/main/resources/templates/**/*.html' -- npm-run-all --serial build:css build:html"\
          """
        )
        .containing(
          """
          "watch:serve": "browser-sync start --no-inject-changes --proxy localhost:8080 --files 'target/classes/templates' 'target/classes/static'"\
          """
        )
        .and()
      .hasFile("postcss.config.js")
        .containing(",require('tailwindcss')")
        .and()
      .hasFile("src/main/resources/static/css/application.css")
        .containing(
          """
          /*! @import */
          @tailwind base;
          @tailwind components;
          @tailwind utilities;
          """
        )
        .and()
      .hasFile("src/main/resources/templates/index.html")
        .containing(
          """
          <main class="flex flex-col min-h-screen w-full justify-center">
            <section
              class="flex flex-col w-full py-16 md:py-24 border-2 border-dashed border-green-500"
            >
              <div
                class="flex flex-col w-full max-w-7xl mx-auto px-4 md:px-8 xl:px-20 gap-8"
              >
                <div class="flex justify-center items-center gap-2">
                  <img
                    class="w-36 h-36"
                    th:src="@{/images/ThymeleafLogo.png}"
                    alt="Thymeleaf Logo"
                  />
                  <h1 class="text-6xl font-bold">Thymeleaf</h1>
                </div>
                <div class="flex justify-center">
                  <div class="text-lg">
                    Welcome to your Spring Boot with Thymeleaf project!
                  </div>
                </div>
              </div>
            </section>
          </main>
          """
        )
        .and()
      .hasFiles("tailwind.config.js")
      .hasFile("src/main/resources/static/images/ThymeleafLogo.png");
    // @formatter:on
  }

  @Test
  void shouldProxyBeUpdatedWhenServerPortPropertyNotDefault() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhipster")
      .put("serverPort", 8081)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
      .containing("browser-sync start --proxy localhost:8081 --files 'target/classes/templates' 'target/classes/static'")
      .notContaining("browser-sync start --proxy localhost:8080 --files 'target/classes/templates' 'target/classes/static'");
    // @formatter:on
  }

  private static ModuleFile appPostcssFile() {
    return new ModuleFile("src/test/resources/projects/thymeleaf/postcss.config.js.mustache", "postcss.config.js");
  }

  private static ModuleFile appCssFile() {
    return new ModuleFile(
      "src/test/resources/projects/thymeleaf/application.css.mustache",
      "src/main/resources/static/css/application.css"
    );
  }

  private static ModuleFile appIndexFile() {
    return new ModuleFile("src/test/resources/projects/thymeleaf/index.html.mustache", "src/main/resources/templates/index.html");
  }
}
