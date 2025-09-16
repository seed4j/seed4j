package com.seed4j.generator.prettier.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.file;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.lintStagedConfigFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.packageJsonFile;
import static org.mockito.Mockito.verify;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PrettierModuleFactoryTest {

  @InjectMocks
  private PrettierModuleFactory factory;

  @Mock
  private NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  @Test
  void shouldBuildModuleWithoutPrettierLintStaged() {
    String folder = TestFileUtils.tmpDirForTest();
    Seed4JModuleProperties properties = properties(folder);

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFileWithoutPrettier())
      .hasFiles(".prettierignore")
      .hasFile(".lintstagedrc.cjs")
      .containing(
        """
        module.exports = {
          '*.{md,json*,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
          '*.pug': ['eslint --fix', 'prettier --write'],
        };
        """
      )
      .and()
      .hasFile(".prettierrc")
      .containing("tabWidth: 2")
      .containing("endOfLine: 'crlf'")
      .containing("@prettier/plugin-xml")
      .containing("prettier-plugin-gherkin")
      .containing("prettier-plugin-java")
      .containing("prettier-plugin-organize-imports")
      .containing("prettier-plugin-packagejson")
      .and()
      .hasFile("package.json")
      .containing(nodeDependency("@prettier/plugin-xml"))
      .containing(nodeDependency("prettier"))
      .containing(nodeDependency("prettier-plugin-gherkin"))
      .containing(nodeDependency("prettier-plugin-java"))
      .containing(nodeDependency("prettier-plugin-organize-imports"))
      .containing(nodeDependency("prettier-plugin-packagejson"))
      .containing(nodeScript("prettier:check", "prettier --check ."))
      .containing(nodeScript("prettier:format", "prettier --write ."));

    verify(nodeLazyPackagesInstaller).runInstallIn(properties.projectFolder(), properties.nodePackageManager());
  }

  @Test
  void shouldBuildModuleWithEmptyLintStaged() {
    String folder = TestFileUtils.tmpDirForTest();
    Seed4JModuleProperties properties = properties(folder);

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile(), lintStagedConfigFile())
      .hasFile(".lintstagedrc.cjs")
      .containing(
        """
        module.exports = {
          '*.{md,json*,yml,html,css,scss,java,xml,feature}': ['prettier --write'],
        };
        """
      );
  }

  private Seed4JModuleProperties properties(String folder) {
    return Seed4JModulesFixture.propertiesBuilder(folder)
      .projectBaseName("testProject")
      .put("projectName", "Test Project")
      .put("indentSize", 2)
      .put("endOfLine", "crlf")
      .build();
  }

  public static Seed4JModulesAssertions.ModuleFile lintStagedConfigFileWithoutPrettier() {
    return file("src/test/resources/projects/init/.lintstagedrc.withoutPrettier.cjs", ".lintstagedrc.cjs");
  }
}
