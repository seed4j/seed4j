package tech.jhipster.lite.generator.client.vue.core.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class VueModulesFactoryTest {

  private static final VueModulesFactory factory = new VueModulesFactory();

  @Test
  void shouldCreateVueModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .basePackage("tech.jhipster.jhlitest")
      .build();

    JHipsterModule module = factory.buildVueModule(properties);

    //@formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFile())
      .hasFiles("documentation/vue.md")
      .hasFile("package.json")
        .containing(nodeDependency("vue"))
        .containing(nodeDependency("@typescript-eslint/parser"))
        .containing(nodeDependency("@vitejs/plugin-vue"))
        .containing(nodeDependency("typescript-eslint"))
        .containing(nodeDependency("globals"))
        .containing(nodeDependency("@vue/test-utils"))
        .containing(nodeDependency("@vue/tsconfig"))
        .containing(nodeDependency("@vitest/coverage-istanbul"))
        .containing(nodeDependency("eslint"))
        .containing(nodeDependency("eslint-config-prettier"))
        .containing(nodeDependency("eslint-plugin-vue"))
        .containing(nodeDependency("jsdom"))
        .containing(nodeDependency("typescript"))
        .containing(nodeDependency("vite"))
        .containing(nodeDependency("vite-tsconfig-paths"))
        .containing(nodeDependency("vitest"))
        .containing(nodeDependency("vitest-sonar-reporter"))
        .containing(nodeDependency("vue-tsc"))
        .containing(nodeDependency("@types/sinon"))
        .containing(nodeDependency("sinon"))
        .containing(nodeDependency("axios"))
        .containing(nodeDependency("vue-router"))
        .containing(nodeDependency("npm-run-all2"))
        .containing(nodeScript("build", "npm-run-all build:*"))
        .containing(nodeScript("build:vue", "vue-tsc -p tsconfig.build.json --noEmit && vite build --emptyOutDir"))
        .containing(nodeScript("dev", "npm-run-all --parallel dev:*"))
        .containing(nodeScript("dev:vite", "vite"))
        .containing(nodeScript("watch", "npm-run-all --parallel watch:*"))
        .containing(nodeScript("watch:tsc", "vue-tsc -p tsconfig.build.json --noEmit --watch"))
        .containing(nodeScript("watch:test", "vitest --"))
        .containing(nodeScript("lint", "eslint ."))
        .containing(nodeScript("preview", "vite preview"))
        .containing(nodeScript("start", "vite"))
        .containing(nodeScript("test", "npm run watch:test"))
        .containing(nodeScript("test:coverage", "vitest run --coverage"))
        .containing(nodeScript("test:watch", "vitest --"))
        .and()
      .hasFile(".lintstagedrc.cjs")
        .containing(
          """
            module.exports = {
              '{src/**/,}*.{ts,vue}': ['eslint --fix', 'prettier --write'],
              '*.{md,json,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
            };
            """
        )
      .and()
      .hasPrefixedFiles("", ".npmrc", "eslint.config.js", "tsconfig.json", "tsconfig.build.json", "vite.config.ts", "vitest.config.ts")
      .hasFiles("src/main/webapp/app/http/AxiosHttp.ts")
      .hasPrefixedFiles("src/test/webapp/unit/http", "AxiosHttp.spec.ts", "AxiosHttpStub.ts", "AxiosStub.ts")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "env.d.ts")
      .hasPrefixedFiles("src/main/webapp/app/common/primary/app", "App.component.ts", "index.ts", "App.html", "AppVue.vue")
      .hasPrefixedFiles("src/main/webapp/content/images", "JHipster-Lite-neon-green.png", "VueLogo.png")
      .hasFiles("src/test/webapp/unit/common/primary/app/App.spec.ts")
      .hasPrefixedFiles("src/main/webapp/app/common/primary/homepage", "Homepage.component.ts", "Homepage.html","HomepageVue.vue", "index.ts")
      .hasFile("src/main/webapp/app/common/primary/homepage/Homepage.component.ts")
        .containing("appName: 'jhiTest'")
        .and()
      .hasFiles("src/test/webapp/unit/common/primary/homepage/Homepage.spec.ts")
      .hasFiles("src/main/webapp/app/router/router.ts", "src/test/webapp/unit/router/Router.spec.ts")
      .hasFile("src/main/webapp/app/main.ts")
        .containing("import router from './router/router';")
        .containing("app.use(router);")
        .and()
      .hasPrefixedFiles("src/main/webapp/app/common/domain", "Logger.ts", "Message.ts")
      .hasFiles("src/main/webapp/app/common/secondary/ConsoleLogger.ts")
      .hasFiles("src/test/webapp/unit/common/domain/Logger.fixture.ts")
      .hasFiles("src/test/webapp/unit/common/secondary/ConsoleLogger.spec.ts")
      .hasFiles("src/main/webapp/app/vue/VueProp.ts")
      .hasFiles(
        "src/test/webapp/unit/vue/vue-prop/ArrayComponentVue.vue",
        "src/test/webapp/unit/vue/vue-prop/ObjectComponentVue.vue",
        "src/test/webapp/unit/vue/vue-prop/VueProp.spec.ts"
      );
    //@formatter:on
  }

  @Test
  void shouldCreatePiniaModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .build();

    JHipsterModule module = factory.buildPiniaModule(properties);

    assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      new ModuleFile("src/test/resources/projects/vue/main.ts.template", "src/main/webapp/app/main.ts")
    )
      .hasFile("package.json")
      .containing(nodeDependency("pinia"))
      .containing(nodeDependency("pinia-plugin-persistedstate"))
      .containing(nodeDependency("@pinia/testing"))
      .and()
      .hasFile("src/main/webapp/app/main.ts")
      .containing("import { createPinia } from 'pinia';")
      .containing("import piniaPersist from 'pinia-plugin-persistedstate';")
      .containing(
        """
        const pinia = createPinia();
        pinia.use(piniaPersist);
        app.use(pinia);
        """
      );
  }
}
