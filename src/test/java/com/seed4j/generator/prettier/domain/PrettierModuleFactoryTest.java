package com.seed4j.generator.prettier.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.file;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.lintStagedConfigFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.nodeDependency;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.nodeScript;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.packageJsonFile;
import static org.mockito.Mockito.verify;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.module.infrastructure.secondary.SeedModulesAssertions;
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
    SeedModuleProperties properties = properties(folder);

    SeedModule module = factory.buildModule(properties);

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
    SeedModuleProperties properties = properties(folder);

    SeedModule module = factory.buildModule(properties);

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

  private SeedModuleProperties properties(String folder) {
    return SeedModulesFixture.propertiesBuilder(folder)
      .projectBaseName("testProject")
      .put("projectName", "Test Project")
      .put("indentSize", 2)
      .put("endOfLine", "crlf")
      .build();
  }

  public static SeedModulesAssertions.ModuleFile lintStagedConfigFileWithoutPrettier() {
    return file("src/test/resources/projects/init/.lintstagedrc.withoutPrettier.cjs", ".lintstagedrc.cjs");
  }
}
