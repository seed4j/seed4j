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
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildVueModule(properties);

    //@formatter:off
    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("package.json")
        .containing(nodeDependency("vue"))
        .containing(nodeDependency("@rushstack/eslint-patch"))
        .containing(nodeDependency("@types/jest"))
        .containing(nodeDependency("@typescript-eslint/parser"))
        .containing(nodeDependency("@vitejs/plugin-vue"))
        .containing(nodeDependency("@vue/eslint-config-typescript"))
        .containing(nodeDependency("@vue/test-utils"))
        .containing(nodeDependency("eslint"))
        .containing(nodeDependency("eslint-config-prettier"))
        .containing(nodeDependency("eslint-plugin-vue"))
        .containing(nodeDependency("jest"))
        .containing(nodeDependency("jest-sonar-reporter"))
        .containing(nodeDependency("jest-transform-stub"))
        .containing(nodeDependency("ts-jest"))
        .containing(nodeDependency("typescript"))
        .containing(nodeDependency("vite"))
        .containing(nodeDependency("vue-jest"))
        .containing(nodeDependency("vue-tsc"))
        .containing(nodeDependency("@types/sinon"))
        .containing(nodeDependency("sinon"))
        .containing(nodeDependency("axios"))
        .containing(nodeDependency("vue-router"))
        .containing("\"build\": \"vue-tsc --noEmit && vite build --emptyOutDir\"")
        .containing("\"dev\": \"vite\"")
        .containing("\"jest\": \"jest src/test/javascript/spec --logHeapUsage --maxWorkers=2 --no-cache\"")
        .containing("\"preview\": \"vite preview\"")
        .containing("\"start\": \"vite\"")
        .containing("\"test\": \"npm run jest --\"")
        .containing("\"test:watch\": \"npm run jest -- --watch\"")
        .containing("  \"jestSonar\": {\n    \"reportPath\": \"target/test-results/jest\",\n    \"reportFile\": \"TESTS-results-sonar.xml\"\n  },")
        .and()
      .hasPrefixedFiles("", ".eslintrc.js", "jest.config.js", "tsconfig.json", "vite.config.ts")
      .hasFiles("src/main/webapp/app/http/AxiosHttp.ts")
      .hasPrefixedFiles("src/test/javascript/spec/http", "AxiosHttp.spec.ts", "AxiosHttpStub.ts", "AxiosStub.ts")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "env.d.ts")
      .hasPrefixedFiles("src/main/webapp/app/common/primary/app", "App.component.ts", "index.ts", "App.html", "App.vue")
      .hasPrefixedFiles("src/main/webapp/content/images", "JHipster-Lite-neon-green.png", "VueLogo.png")
      .hasFiles("src/test/javascript/spec/common/primary/app/App.spec.ts")
      .hasPrefixedFiles("src/main/webapp/app/common/primary/homepage", "Homepage.component.ts", "Homepage.html","Homepage.vue", "index.ts")
      .hasFiles("src/test/javascript/spec/common/primary/homepage/Homepage.spec.ts")
      .hasFiles("src/main/webapp/app/router/router.ts", "src/test/javascript/spec/router/Router.spec.ts")
      .hasFile("src/main/webapp/app/main.ts")
        .containing("import router from './router/router';")
        .containing("app.use(router);")
        .and()
      .hasPrefixedFiles("src/main/webapp/app/common/domain", "Logger.ts", "Message.ts")
      .hasFiles("src/main/webapp/app/common/secondary/ConsoleLogger.ts")
      .hasFiles("src/test/javascript/spec/common/domain/Logger.fixture.ts")
      .hasFiles("src/test/javascript/spec/common/secondary/ConsoleLogger.spec.ts");
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
      .containing("import {createPinia} from 'pinia';")
      .containing("import piniaPersist from 'pinia-plugin-persist'")
      .containing(
        """
                const pinia = createPinia();
                pinia.use(piniaPersist);
                app.use(pinia);
                """
      );
  }

  private static String nodeDependency(String dependency) {
    return "\"" + dependency + "\": \"";
  }
}
