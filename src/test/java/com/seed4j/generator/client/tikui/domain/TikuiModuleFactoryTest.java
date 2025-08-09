package com.seed4j.generator.client.tikui.domain;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class TikuiModuleFactoryTest {

  private static final TikuiModuleFactory factory = new TikuiModuleFactory();

  @Nested
  class Prettier {

    @Test
    void shouldBuildModuleWithPugFormat() {
      SeedModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, packageJsonFile(), prettierConfigFile())
        .hasFile("package.json")
        .containing(nodeDependency("@prettier/plugin-pug"))
        .and()
        .hasFile(".prettierrc")
        .containing(prettierConfig());
    }

    private static ModuleFile prettierConfigFile() {
      return file("src/main/resources/generator/prettier/.prettierrc.mustache", ".prettierrc");
    }

    private static String prettierConfig() {
      return """
        - '@prettier/plugin-pug'
      """;
    }
  }

  @Nested
  class Vue {

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
  class React {

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

  @Nested
  class Angular {

    @Test
    void shouldBuildModuleOnAngularProject() {
      assertThatTikuiModule(proxyConfFile(), indexFile()).hasFile("proxy.conf.json").containing(styleProxy());
    }

    public static ModuleFile proxyConfFile() {
      return file("src/main/resources/generator/client/angular/core/proxy.conf.json.mustache", "proxy.conf.json");
    }

    private static ModuleFile indexFile() {
      return file("src/main/resources/generator/client/angular/core/src/main/webapp/index.html.mustache", "src/main/webapp/index.html");
    }

    private String styleProxy() {
      return """
        "/style": {
          "target": "http://localhost:9005",
          "secure": false
        },\
      """;
    }
  }

  private static String[] componentFiles(String name) {
    return Set.of("_" + name + ".scss", name + ".code.pug", name + ".md", name + ".mixin.pug", name + ".render.pug").toArray(String[]::new);
  }

  private static JHipsterModuleAsserter assertThatTikuiModule(ModuleFile proxyFile, ModuleFile indexFile) {
    SeedModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

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
      .hasPrefixedFiles("src/main/style/atom/button", componentFiles("button"))
      .hasPrefixedFiles("src/main/style/atom/input-text", componentFiles("input-text"))
      .hasPrefixedFiles("src/main/style/atom/label", componentFiles("label"))
      .hasPrefixedFiles("src/main/style/atom/page-title", componentFiles("page-title"))
      .hasPrefixedFiles("src/main/style/atom/paragraph", componentFiles("paragraph"))
      .hasPrefixedFiles("src/main/style/molecule", "_molecule.scss", "molecule.pug")
      .hasPrefixedFiles("src/main/style/molecule/field", componentFiles("field"))
      .hasPrefixedFiles("src/main/style/molecule/toast", componentFiles("toast"))
      .hasPrefixedFiles("src/main/style/organism", "_organism.scss", "organism.pug")
      .hasPrefixedFiles("src/main/style/organism/columns", componentFiles("columns"))
      .hasPrefixedFiles("src/main/style/organism/lines", componentFiles("lines"))
      .hasPrefixedFiles("src/main/style/organism/toasts", componentFiles("toasts"))
      .hasPrefixedFiles("src/main/style/quark", "_placeholder.scss", "_spacing.scss")
      .hasPrefixedFiles("src/main/style/template", "_template.scss", "template.pug")
      .hasPrefixedFiles("src/main/style/template/template-page", componentFiles("template-page"))
      .hasPrefixedFiles("src/main/style/template/toasting", componentFiles("toasting"))
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
      )
      .hasPrefixedFiles("src/main/style/variables", "_breakpoint.scss");
  }
}
