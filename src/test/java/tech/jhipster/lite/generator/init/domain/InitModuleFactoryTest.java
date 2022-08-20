package tech.jhipster.lite.generator.init.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleAsserter;
import tech.jhipster.lite.npm.domain.NpmVersion;
import tech.jhipster.lite.npm.domain.NpmVersionSource;
import tech.jhipster.lite.npm.domain.NpmVersions;

@UnitTest
@ExtendWith(MockitoExtension.class)
class InitModuleFactoryTest {

  @Mock
  private NpmVersions npmVersions;

  @InjectMocks
  private InitModuleFactory factory;

  @Test
  void shouldBuildFullModule() {
    JHipsterModuleProperties properties = properties(TestFileUtils.tmpDirForTest());
    when(npmVersions.get("node", NpmVersionSource.COMMON)).thenReturn(new NpmVersion("16.0.0"));

    JHipsterModule module = factory.buildFullModule(properties);

    assertMinimalModule(module)
      .createFiles(".lintstagedrc.js", ".prettierignore", ".prettierrc")
      .createExecutableFiles(".husky/pre-commit")
      .createFile("package.json")
      .containing("test-project")
      .containing(nodeDependency("@prettier/plugin-xml"))
      .containing(nodeDependency("husky"))
      .containing(nodeDependency("lint-staged"))
      .containing(nodeDependency("prettier"))
      .containing(nodeDependency("prettier-plugin-java"))
      .containing(nodeDependency("prettier-plugin-packagejson"))
      .containing(nodeScript("prepare", "husky install"))
      .containing(nodeScript("prettier:check", "prettier --check \\\"{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}\\\""))
      .containing(nodeScript("prettier:format", "prettier --write \\\"{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}\\\""))
      .containing("\"node\": \">=16.0.0\"");
  }

  @Test
  void shouldBuildMinimalModule() {
    JHipsterModuleProperties properties = properties(TestFileUtils.tmpDirForTest());

    JHipsterModule module = factory.buildMinimalModule(properties);

    assertMinimalModule(module);
  }

  private JHipsterModuleProperties properties(String folder) {
    return JHipsterModulesFixture
      .propertiesBuilder(folder)
      .projectBaseName("testProject")
      .put("projectName", "Test Project")
      .put("prettierDefaultIndent", 2)
      .put("editorConfigEndOfLine", "lf")
      .put("prettierEndOfLine", "lf")
      .build();
  }

  private ModuleAsserter assertMinimalModule(JHipsterModule module) {
    return assertThatModule(module)
      .createFile("README.md")
      .containing("# Test Project")
      .and()
      .createFiles(".gitignore", ".gitattributes")
      .createFile(".editorconfig")
      .containing("end_of_line = lf")
      .containing("indent_size = 2")
      .and()
      .createFiles(".eslintignore");
  }
}
