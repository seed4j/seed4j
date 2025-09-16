package com.seed4j.generator.server.springboot.thymeleaf.template.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.regex;
import static com.seed4j.module.domain.Seed4JModule.runScriptCommandWith;
import static com.seed4j.module.domain.Seed4JModule.scriptCommand;
import static com.seed4j.module.domain.Seed4JModule.scriptKey;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainResources;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.replacement.ReplacementCondition.notMatchingRegex;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.replacement.ElementReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public class ThymeleafTemplateModuleFactory {

  private static final String PROPERTIES = "properties";
  private static final String TEMPLATES = "templates";
  private static final String TEMPLATES_LAYOUT = "templates/layout";
  private static final String STATIC_CSS = "static/css";
  private static final String STATIC_IMAGES = "static/images";
  private static final String MAIN_HTML = "main.html";
  private static final String POSTCSS_CONFIG_JS = "postcss.config.js";
  private static final String TAILWIND_CONFIG_JS = "tailwind.config.js";

  private static final Seed4JSource SOURCE = from("server/springboot/thymeleaf/template");
  private static final Seed4JSource DOCUMENTATION_SOURCE = SOURCE.append("documentation");
  private static final Seed4JSource RESOURCES_SOURCE = SOURCE.append("src/main/resources");

  private static final Seed4JProjectFilePath MAIN_RESOURCES_PATH = path("src/main/resources");
  private static final Seed4JDestination DESTINATION = to(MAIN_RESOURCES_PATH.get());
  private static final Seed4JDestination ROOT_DESTINATION = to(".");

  private static final String MAIN_SCRIPT_NEEDLE = "<!-- seed4j-needle-thymeleaf-main-script -->";
  private static final String THYMELEAF_POSTCSS_PLUGINS_NEEDLE = "// seed4j-needle-thymeleaf-postcss-plugins";
  private static final String THYMELEAF_CSS_NEEDLE = "/* seed4j-needle-thymeleaf-css */";
  private static final Pattern WELCOME_THYMELEAF_MESSAGE_PATTERN = Pattern.compile(
    "<div>Welcome to your Spring Boot with Thymeleaf project!</div>"
  );
  private static final ElementReplacer EXISTING_WELCOME_THYMELEAF_MESSAGE_NEEDLE = regex(
    notMatchingRegex(WELCOME_THYMELEAF_MESSAGE_PATTERN),
    WELCOME_THYMELEAF_MESSAGE_PATTERN
  );

  private static final String TAILWINDCSS_REQUIRE = "            ,require('tailwindcss')";
  private static final String TAILWINDCSS_SETUP = """
    /*! @import */
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
    """;
  private static final String TAILWINDCSS_WELCOME_THYMELEAF_MESSAGE = """
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
    """;

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Thymeleaf"), DOCUMENTATION_SOURCE.template("thymeleaf.md"))
      .packageJson()
        .addDevDependency(packageName("@babel/cli"), COMMON)
        .addDevDependency(packageName("autoprefixer"), COMMON)
        .addDevDependency(packageName("browser-sync"), COMMON)
        .addDevDependency(packageName("cssnano"), COMMON)
        .addDevDependency(packageName("mkdirp"), COMMON)
        .addDevDependency(packageName("npm-run-all2"), COMMON)
        .addDevDependency(packageName("onchange"), COMMON)
        .addDevDependency(packageName("path-exists-cli"), COMMON)
        .addDevDependency(packageName("postcss"), COMMON)
        .addDevDependency(packageName("postcss-cli"), COMMON)
        .addDevDependency(packageName("recursive-copy-cli"), COMMON)
        .addScript(scriptKey("build"), scriptCommand("npm-run-all --parallel build:*"))
        .addScript(scriptKey("build:html"), scriptCommand("recursive-copy 'src/main/resources/templates' {{projectBuildDirectory}}/classes/templates -w"))
        .addScript(scriptKey("build:css"), scriptCommand("mkdirp {{projectBuildDirectory}}/classes/static/css && postcss src/main/resources/static/css/*.css -d {{projectBuildDirectory}}/classes/static/css"))
        .addScript(scriptKey("build:js"), scriptCommand("path-exists src/main/resources/static/js && (mkdirp {{projectBuildDirectory}}/classes/static/js && babel src/main/resources/static/js/ --out-dir {{projectBuildDirectory}}/classes/static/js/) || echo 'No src/main/resources/static/js directory found.'"))
        .addScript(scriptKey("build:svg"), scriptCommand("path-exists src/main/resources/static/svg && recursive-copy 'src/main/resources/static/svg' {{projectBuildDirectory}}/classes/static/svg -w -f '**/*.svg' || echo 'No src/main/resources/static/svg directory found.'"))
        .addScript(scriptKey("build-prod"), scriptCommand("NODE_ENV='production' npm-run-all --parallel build-prod:*"))
        .addScript(scriptKey("build-prod:html"), runScriptCommandWith(properties.nodePackageManager(), "build:html"))
        .addScript(scriptKey("build-prod:css"), runScriptCommandWith(properties.nodePackageManager(), "build:css"))
        .addScript(scriptKey("build-prod:js"), scriptCommand("path-exists src/main/resources/static/js && (mkdirp {{projectBuildDirectory}}/classes/static/js && babel src/main/resources/static/js/ --minified --out-dir {{projectBuildDirectory}}/classes/static/js/) || echo 'No src/main/resources/static/js directory found.'"))
        .addScript(scriptKey("build-prod:svg"), runScriptCommandWith(properties.nodePackageManager(), "build:svg"))
        .addScript(scriptKey("watch"), scriptCommand("npm-run-all --parallel watch:*"))
        .addScript(scriptKey("watch:html"), scriptCommand("onchange 'src/main/resources/templates/**/*.html' -- npm run build:html"))
        .addScript(scriptKey("watch:css"), scriptCommand("onchange 'src/main/resources/static/css/**/*.css' -- npm run build:css"))
        .addScript(scriptKey("watch:js"), scriptCommand("onchange 'src/main/resources/static/js/**/*.js' -- npm run build:js"))
        .addScript(scriptKey("watch:svg"), scriptCommand("onchange 'src/main/resources/static/svg/**/*.svg' -- npm run build:svg"))
        .addScript(scriptKey("watch:serve"), scriptCommand("browser-sync start --proxy localhost:%s --files '{{projectBuildDirectory}}/classes/templates' '{{projectBuildDirectory}}/classes/static'".formatted(properties.serverPort().get())))
        .and()
      .files()
        .add(RESOURCES_SOURCE.append(TEMPLATES).template("index.html"), toSrcMainResources().append(TEMPLATES).append("index.html"))
        .add(RESOURCES_SOURCE.append(TEMPLATES_LAYOUT).template(MAIN_HTML), toSrcMainResources().append(TEMPLATES_LAYOUT).append(MAIN_HTML))
        .add(RESOURCES_SOURCE.append(STATIC_CSS).template("application.css"), DESTINATION.append(STATIC_CSS).append("application.css"))
        .add(SOURCE.template(POSTCSS_CONFIG_JS), ROOT_DESTINATION.append(POSTCSS_CONFIG_JS))
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildTailwindcssModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("tailwindcss"), COMMON)
        .addScript(scriptKey("watch:html"), scriptCommand("onchange 'src/main/resources/templates/**/*.html' -- npm-run-all --serial build:css build:html"))
        .addScript(scriptKey("watch:serve"), scriptCommand("browser-sync start --no-inject-changes --proxy localhost:%s --files '{{projectBuildDirectory}}/classes/templates' '{{projectBuildDirectory}}/classes/static'".formatted(properties.serverPort().get())))
        .and()
      .mandatoryReplacements()
        .in(path(POSTCSS_CONFIG_JS))
          .add(lineBeforeText(THYMELEAF_POSTCSS_PLUGINS_NEEDLE), TAILWINDCSS_REQUIRE)
          .and()
        .in(path("src/main/resources/static/css/application.css"))
          .add(lineBeforeText(THYMELEAF_CSS_NEEDLE), TAILWINDCSS_SETUP)
          .and()
        .in(path("src/main/resources/templates/index.html"))
          .add(EXISTING_WELCOME_THYMELEAF_MESSAGE_NEEDLE, TAILWINDCSS_WELCOME_THYMELEAF_MESSAGE)
          .and()
        .and()
      .files()
        .add(SOURCE.template(TAILWIND_CONFIG_JS), ROOT_DESTINATION.append(TAILWIND_CONFIG_JS))
        .add(RESOURCES_SOURCE.append(STATIC_IMAGES).file("ThymeleafLogo.png"), DESTINATION.append(STATIC_IMAGES).append("ThymeleafLogo.png"))
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildHtmxWebjarsModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mandatoryReplacements()
        .in(MAIN_RESOURCES_PATH.append(TEMPLATES_LAYOUT).append(MAIN_HTML))
          .add(lineBeforeText(MAIN_SCRIPT_NEEDLE), webjarsScriptTag("htmx.org/dist/htmx.min.js"))
          .and()
      .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildAlpineWebjarsModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mandatoryReplacements()
        .in(MAIN_RESOURCES_PATH.append(TEMPLATES_LAYOUT).append(MAIN_HTML))
          .add(lineBeforeText(MAIN_SCRIPT_NEEDLE), webjarsScriptTag("alpinejs/dist/cdn.js"))
          .and()
      .and()
      .build();
    // @formatter:on
  }

  private String webjarsScriptTag(String webjarsLocation) {
    return "<script type=\"text/javascript\" th:src=\"@{/webjars/%s}\"></script>".formatted(webjarsLocation);
  }
}
