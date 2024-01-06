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
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildVueModule(properties);

    //@formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFile())
      .hasFiles("documentation/vue.md")
      .hasFile("package.json")
        .containing(nodeDependency("vue"))
        .containing(nodeDependency("@rushstack/eslint-patch"))
        .containing(nodeDependency("@typescript-eslint/parser"))
        .containing(nodeDependency("@vitejs/plugin-vue"))
        .containing(nodeDependency("@vue/eslint-config-typescript"))
        .containing(nodeDependency("@vue/eslint-config-prettier"))
        .containing(nodeDependency("@vue/test-utils"))
        .containing(nodeDependency("@vitest/coverage-istanbul"))
        .containing(nodeDependency("eslint"))
        .containing(nodeDependency("eslint-plugin-vue"))
        .containing(nodeDependency("jsdom"))
        .containing(nodeDependency("typescript"))
        .containing(nodeDependency("vite"))
        .containing(nodeDependency("vitest"))
        .containing(nodeDependency("vitest-sonar-reporter"))
        .containing(nodeDependency("vue-tsc"))
        .containing(nodeDependency("@types/sinon"))
        .containing(nodeDependency("sinon"))
        .containing(nodeDependency("axios"))
        .containing(nodeDependency("vue-router"))
        .containing("\"build\": \"vue-tsc -p tsconfig.build.json --noEmit && vite build --emptyOutDir\"")
        .containing("\"dev\": \"vite\"")
        .containing("\"lint\": \"eslint --ext .js,.ts,.vue src\"")
        .containing("\"preview\": \"vite preview\"")
        .containing("\"start\": \"vite\"")
        .containing("\"test\": \"vitest run --coverage\"")
        .containing("\"test:watch\": \"vitest --\"")
        .and()
      .hasFile(".lintstagedrc.js")
        .containing(
          """
            module.exports = {
              '{src/**/,}*.{js,ts,tsx,vue}': ['eslint --fix'],
              '{src/**/,}*.{md,json,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
            };
            """
        )
      .and()
      .hasPrefixedFiles("", ".eslintignore", ".eslintrc.js", "tsconfig.json", "tsconfig.build.json", "vite.config.mts", "vitest.config.mts")
      .hasFiles("src/main/webapp/app/http/AxiosHttp.ts")
      .hasPrefixedFiles("src/test/javascript/spec/http", "AxiosHttp.spec.ts", "AxiosHttpStub.ts", "AxiosStub.ts")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "env.d.ts")
      .hasPrefixedFiles("src/main/webapp/app/common/primary/app", "App.component.ts", "index.ts", "App.html", "AppVue.vue")
      .hasPrefixedFiles("src/main/webapp/content/images", "JHipster-Lite-neon-green.png", "VueLogo.png")
      .hasFiles("src/test/javascript/spec/common/primary/app/App.spec.ts")
      .hasPrefixedFiles("src/main/webapp/app/common/primary/homepage", "Homepage.component.ts", "Homepage.html","HomepageVue.vue", "index.ts")
      .hasFile("src/main/webapp/app/common/primary/homepage/Homepage.component.ts")
        .containing("appName: 'jhiTest'")
        .and()
      .hasFiles("src/test/javascript/spec/common/primary/homepage/Homepage.spec.ts")
      .hasFiles("src/main/webapp/app/router/router.ts", "src/test/javascript/spec/router/Router.spec.ts")
      .hasFile("src/main/webapp/app/main.ts")
        .containing("import router from './router/router';")
        .containing("app.use(router);")
        .and()
      .hasPrefixedFiles("src/main/webapp/app/common/domain", "Logger.ts", "Message.ts")
      .hasFiles("src/main/webapp/app/common/secondary/ConsoleLogger.ts")
      .hasFiles("src/test/javascript/spec/common/domain/Logger.fixture.ts")
      .hasFiles("src/test/javascript/spec/common/secondary/ConsoleLogger.spec.ts")
      .hasFiles("src/main/webapp/app/vue/VueProp.ts")
      .hasFiles(
        "src/test/javascript/spec/vue/vue-prop/ArrayComponentVue.vue",
        "src/test/javascript/spec/vue/vue-prop/ObjectComponentVue.vue",
        "src/test/javascript/spec/vue/vue-prop/VueProp.spec.ts"
      );
    //@formatter:on
  }

  @Test
  void shouldCreatePiniaModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildPiniaModule(properties);

    assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      new ModuleFile("src/test/resources/projects/vue/main.ts.template", "src/main/webapp/app/main.ts")
    )
      .hasFile("package.json")
      .containing(nodeDependency("pinia"))
      .containing(nodeDependency("pinia-plugin-persist"))
      .containing(nodeDependency("@pinia/testing"))
      .and()
      .hasFile("src/main/webapp/app/main.ts")
      .containing("import { createPinia } from 'pinia';")
      .containing("import piniaPersist from 'pinia-plugin-persist';")
      .containing(
        """
        const pinia = createPinia();
        pinia.use(piniaPersist);
        app.use(pinia);
        """
      );
  }
}
