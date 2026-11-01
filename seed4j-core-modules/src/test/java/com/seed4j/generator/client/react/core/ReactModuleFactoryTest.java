package com.seed4j.generator.client.react.core;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.eslintConfigFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.file;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.lintStagedConfigFileWithPrettier;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.packageJsonFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.tsConfigFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.vitestConfigFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices.FakeNodeLazyPackagesInstaller.Installation;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.ModuleFile;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class ReactModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final ReactModuleFactory factory = new ReactModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("seed4j")
      .build();
    Seed4JModule module = factory.buildModule(properties);

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
      .containing(nodeDependency("@eslint-react/eslint-plugin"))
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

    assertThat(seed4JModuleServices.nodeLazyPackagesInstaller().installations()).containsExactly(
      new Installation(properties.projectFolder(), properties.nodePackageManager())
    );
  }

  @Test
  void shouldViteConfigBeUpdatedWhenServerPortPropertyNotDefault() {
    Seed4JModule module = factory.buildModule(
      Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").put("serverPort", 8081).build()
    );

    assertThatModuleWithFiles(module, packageJsonFile(), eslintConfigFile(), tsConfigFile(), vitestConfigFile())
      .hasFile("vite.config.ts")
      .containing("localhost:8081")
      .notContaining("localhost:8080");
  }

  @Test
  void shouldViteConfigBeDefaultWhenServerPortPropertyMissing() {
    Seed4JModule module = factory.buildModule(
      Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build()
    );

    assertThatModuleWithFiles(module, packageJsonFile(), eslintConfigFile(), tsConfigFile(), vitestConfigFile())
      .hasFile("vite.config.ts")
      .containing("localhost:8080");
  }

  @Test
  void shouldFailWhenEslintConfigRecommendedIsMissing() throws IOException {
    Seed4JModule module = factory.buildModule(
      Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build()
    );
    Path customEslint = Path.of(TestFileUtils.tmpDirForTest(), "eslint.config.js");
    Files.createDirectories(customEslint.getParent());
    Files.writeString(
      customEslint,
      Files.readString(Path.of("src/main/resources/generator/typescript/eslint.config.js.mustache")).replace(
        "...typescript.configs.recommended",
        "...typescript.configs.strict"
      )
    );
    ModuleFile customEslintFile = file(customEslint.toString(), "eslint.config.js");

    assertThatThrownBy(() ->
      assertThatModuleWithFiles(module, packageJsonFile(), customEslintFile, tsConfigFile(), vitestConfigFile())
    )
      .isInstanceOf(GeneratorException.class)
      .hasMessageContaining("Can't find");
  }

  private String nodeScript(String key, String value) {
    return "\"" + key + "\": \"" + value + "\"";
  }
}
