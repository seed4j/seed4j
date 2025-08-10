package com.seed4j.generator.client.vue.core.domain;

import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;
import static org.mockito.Mockito.verify;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class VueModuleFactoryTest {

  @InjectMocks
  private VueModuleFactory factory;

  @Mock
  private NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  @Test
  void shouldBuildVueModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithPrettier(), tsConfigFile(), vitestConfigFile(), eslintConfigFile())
      .hasFiles("documentation/vue.md")
      .hasFile("package.json")
        .notContaining(nodeDependency("@tsconfig/recommended"))
        .containing(nodeDependency("vue"))
        .containing(nodeDependency("@vitejs/plugin-vue"))
        .containing(nodeDependency("@vue/test-utils"))
        .containing(nodeDependency("@vue/tsconfig"))
        .containing(nodeDependency("eslint-plugin-vue"))
        .containing(nodeDependency("jsdom"))
        .containing(nodeDependency("vite"))
        .containing(nodeDependency("vue-tsc"))
        .containing(nodeDependency("axios"))
        .containing(nodeDependency("piqure"))
        .containing(nodeScript("build", "npm-run-all build:*"))
        .containing(nodeScript("build:tsc", "vue-tsc -p tsconfig.build.json --noEmit"))
        .containing(nodeScript("build:vite", "vite build --emptyOutDir"))
        .containing(nodeScript("dev", "npm-run-all --parallel dev:*"))
        .containing(nodeScript("dev:vite", "vite"))
        .containing(nodeScript("watch:tsc", "npm run build:tsc -- --watch"))
        .containing(nodeScript("preview", "vite preview"))
        .containing(nodeScript("start", "vite"))
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
      .hasPrefixedFiles("", "eslint.config.js", "tsconfig.build.json", "vite.config.ts")
      .hasFile("tsconfig.json")
        .matchingSavedSnapshot()
        .and()
      .hasFile("vitest.config.ts")
        .matchingSavedSnapshot()
        .and()
      .hasFile("eslint.config.js")
        .matchingSavedSnapshot()
      .and()
      .hasFiles("src/main/webapp/app/shared/http/infrastructure/secondary/AxiosHttp.ts")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "env.d.ts", "AppVue.vue", "injections.ts", "main.ts")
      .hasPrefixedFiles("src/main/webapp/app/home","infrastructure/primary/HomepageVue.vue")
      .hasPrefixedFiles("src/main/webapp/content/images", "seed4j_logo-name.png", "VueLogo.png")
      .hasFiles("src/test/webapp/unit/Dummy.spec.ts")
      .hasFiles("src/test/webapp/unit/shared/http/infrastructure/secondary/AxiosHttp.spec.ts")
      .hasFiles("src/test/webapp/unit/shared/http/infrastructure/secondary/AxiosStub.ts");
    // @formatter:on
    verify(nodeLazyPackagesInstaller).runInstallIn(properties.projectFolder(), properties.nodePackageManager());
  }

  @Test
  void shouldBuildVueModuleWithPnpm() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).nodePackageManager(PNPM).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      lintStagedConfigFileWithPrettier(),
      tsConfigFile(),
      vitestConfigFile(),
      eslintConfigFile()
    )
      .hasFile("package.json")
      .containing(nodeScript("watch:tsc", "pnpm run build:tsc -- --watch"));
  }
}
