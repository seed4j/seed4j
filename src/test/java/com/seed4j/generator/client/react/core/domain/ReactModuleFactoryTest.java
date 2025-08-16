package com.seed4j.generator.client.react.core.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.eslintConfigFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.lintStagedConfigFileWithPrettier;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.packageJsonFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.tsConfigFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.vitestConfigFile;
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
class ReactModuleFactoryTest {

  @InjectMocks
  private ReactModuleFactory factory;

  @Mock
  private NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build();
    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      lintStagedConfigFileWithPrettier(),
      eslintConfigFile(),
      tsConfigFile(),
      vitestConfigFile()
    )
      .hasFile("package.json")
      .notContaining(nodeDependency("@tsconfig/recommended"))
      .containing(nodeDependency("@testing-library/dom"))
      .containing(nodeDependency("@testing-library/react"))
      .containing(nodeDependency("@types/node"))
      .containing(nodeDependency("@types/react"))
      .containing(nodeDependency("@types/react-dom"))
      .containing(nodeDependency("@tsconfig/vite-react"))
      .containing(nodeDependency("@vitejs/plugin-react"))
      .containing(nodeDependency("eslint-plugin-react"))
      .containing(nodeDependency("jsdom"))
      .containing(nodeDependency("vite"))
      .containing(nodeDependency("react"))
      .containing(nodeDependency("react-dom"))
      .containing(nodeDependency("piqure"))
      .containing(nodeScript("dev", "npm-run-all --parallel dev:*"))
      .containing(nodeScript("dev:vite", "vite"))
      .containing(nodeScript("build", "npm-run-all build:*"))
      .containing(nodeScript("build:tsc", "tsc -b"))
      .containing(nodeScript("build:vite", "vite build --emptyOutDir"))
      .containing(nodeScript("preview", "vite preview"))
      .containing(nodeScript("start", "vite"))
      .and()
      .hasFile(".lintstagedrc.cjs")
      .containing(
        """
        module.exports = {
          '{src/**/,}*.{ts,tsx}': ['eslint --fix', 'prettier --write'],
          '*.{md,json,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
        };
        """
      )
      .and()
      .hasFile("eslint.config.js")
      .matchingSavedSnapshot()
      .and()
      .hasFile("tsconfig.json")
      .matchingSavedSnapshot()
      .and()
      .hasFile("vitest.config.ts")
      .matchingSavedSnapshot()
      .and()
      .hasFiles("vite.config.ts")
      .hasFiles("src/main/webapp/index.html")
      .hasPrefixedFiles("src/main/webapp/app", "index.css", "index.tsx", "vite-env.d.ts", "injections.ts")
      .hasFiles("src/test/webapp/unit/home/infrastructure/primary/HomePage.spec.tsx")
      .hasFile("src/main/webapp/app/home/infrastructure/primary/HomePage.tsx")
      .containing("import './HomePage.css';")
      .and()
      .hasFiles("src/main/webapp/app/home/infrastructure/primary/HomePage.css")
      .hasPrefixedFiles("src/main/webapp/assets", "ReactLogo.png", "seed4j_logo-name.png");

    verify(nodeLazyPackagesInstaller).runInstallIn(properties.projectFolder(), properties.nodePackageManager());
  }

  @Test
  void shouldViteConfigBeUpdatedWhenServerPortPropertyNotDefault() {
    SeedModule module = factory.buildModule(
      SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").put("serverPort", 8081).build()
    );

    assertThatModuleWithFiles(module, packageJsonFile(), eslintConfigFile(), tsConfigFile(), vitestConfigFile())
      .hasFile("vite.config.ts")
      .containing("localhost:8081")
      .notContaining("localhost:8080");
  }

  @Test
  void shouldViteConfigBeDefaultWhenServerPortPropertyMissing() {
    SeedModule module = factory.buildModule(
      SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build()
    );

    assertThatModuleWithFiles(module, packageJsonFile(), eslintConfigFile(), tsConfigFile(), vitestConfigFile())
      .hasFile("vite.config.ts")
      .containing("localhost:8080");
  }

  private String nodeScript(String key, String value) {
    return "\"" + key + "\": \"" + value + "\"";
  }
}
