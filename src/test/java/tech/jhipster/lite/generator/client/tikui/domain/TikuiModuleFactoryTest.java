package tech.jhipster.lite.generator.client.tikui.domain;

import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class TikuiModuleFactoryTest {

  private static final TikuiModuleFactory factory = new TikuiModuleFactory();

  @Nested
  class VueTest {

    @Test
    void shouldBuildModuleOnVueProject() {
      assertThatTikuiModule(viteConfigFile(), indexFile()).hasFile("vite.config.ts").containing(styleProxy());
    }

    private static String styleProxy() {
      return """
          port: 9000,
          proxy: {
            '/style': {
              ws: true,
              changeOrigin: true,
              rewrite: path => path.replace('/style', ''),
              target: 'http://localhost:9005',
            },
          },\
      """;
    }

    public static ModuleFile viteConfigFile() {
      return file("src/main/resources/generator/client/vue/vite.config.ts.mustache", "vite.config.ts");
    }

    private static ModuleFile indexFile() {
      return file("src/main/resources/generator/client/vue/webapp/index.html.mustache", "src/main/webapp/index.html");
    }
  }

  @Nested
  class ReactTest {

    @Test
    void shouldBuildModuleOnReactProject() {
      assertThatTikuiModule(viteConfigFile(), indexFile()).hasFile("vite.config.ts").containing(styleProxy());
    }

    private static String styleProxy() {
      return """
          proxy: {
            '/style': {
              ws: true,
              changeOrigin: true,
              rewrite: path => path.replace('/style', ''),
              target: 'http://localhost:9005',
            },
            '/api': {
              target: 'http://localhost:8080',
              changeOrigin: true,
            },
          },\
      """;
    }

    public static ModuleFile viteConfigFile() {
      return file("src/main/resources/generator/client/react/core/vite.config.ts.mustache", "vite.config.ts");
    }

    private static ModuleFile indexFile() {
      return file("src/main/resources/generator/client/react/core/src/main/webapp/index.html.mustache", "src/main/webapp/index.html");
    }
  }

  private static JHipsterModuleAsserter assertThatTikuiModule(ModuleFile proxyFile, ModuleFile indexFile) {
    JHipsterModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    return assertThatModuleWithFiles(module, packageJsonFile(), proxyFile, indexFile)
      .hasFile("package.json")
      .containing(nodeDependency("@tikui/core"))
      .containing(nodeDependency("stylelint"))
      .containing(nodeDependency("stylelint-config-concentric-order"))
      .containing(nodeDependency("stylelint-config-standard-scss"))
      .containing(nodeDependency("stylelint-order"))
      .containing(nodeDependency("tikuidoc-tikui"))
      .containing(nodeScript("build:tikui", "tikui-core build"))
      .containing(nodeScript("dev:tikui", "tikui-core serve"))
      .and()
      .hasFiles("tikuiconfig.json", ".stylelintrc.json")
      .hasFile(".gitignore")
      .containing(".tikui-cache")
      .and()
      .hasFile("src/main/webapp/index.html")
      .containing("    <link rel=\"stylesheet\" href=\"/style/tikui.css\" />")
      .and()
      .hasPrefixedFiles("src/main/style", "tikui.scss", "favicon.ico", "index.pug", "layout.pug", "layout-documentation.pug")
      .hasPrefixedFiles("src/main/style/atom", "_atom.scss", "atom.pug")
      .hasPrefixedFiles(
        "src/main/style/atom/button",
        "_button.scss",
        "button.code.pug",
        "button.md",
        "button.mixin.pug",
        "button.render.pug"
      )
      .hasPrefixedFiles(
        "src/main/style/atom/input-text",
        "_input-text.scss",
        "input-text.code.pug",
        "input-text.md",
        "input-text.mixin.pug",
        "input-text.render.pug"
      )
      .hasPrefixedFiles("src/main/style/atom/label", "_label.scss", "label.code.pug", "label.md", "label.mixin.pug", "label.render.pug")
      .hasPrefixedFiles("src/main/style/molecule", "_molecule.scss", "molecule.pug")
      .hasPrefixedFiles("src/main/style/molecule/field", "_field.scss", "field.code.pug", "field.md", "field.mixin.pug", "field.render.pug")
      .hasPrefixedFiles("src/main/style/organism", "_organism.scss", "organism.pug")
      .hasPrefixedFiles("src/main/style/organism/lines", "_lines.scss", "lines.code.pug", "lines.md", "lines.mixin.pug", "lines.render.pug")
      .hasPrefixedFiles("src/main/style/quark", "_placeholder.scss", "_spacing.scss")
      .hasPrefixedFiles("src/main/style/template", "template.pug")
      .hasPrefixedFiles(
        "src/main/style/token",
        "spacing/_spacings.scss",
        "spacing/_vars.scss",
        "_animations.scss",
        "_color.scss",
        "_font.scss",
        "_gradient.scss",
        "_radius.scss",
        "_size.scss",
        "_token.scss"
      );
  }
}
