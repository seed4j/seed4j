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
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
@ExtendWith(MockitoExtension.class)
class InitModuleFactoryTest {

  @Mock
  private GitRepository git;

  @InjectMocks
  private InitModuleFactory factory;

  @Test
  void shouldBuildFullModule() {
    String folder = TestFileUtils.tmpDirForTest();
    JHipsterModuleProperties properties = properties(folder);

    JHipsterModule module = factory.buildFullModule(properties);

    assertMinimalModule(folder, module)
      .hasFiles(".lintstagedrc.js", ".prettierignore", ".prettierrc")
      .hasExecutableFiles(".husky/pre-commit")
      .hasFile("package.json")
      .containing("test-project")
      .containing(nodeDependency("@prettier/plugin-xml"))
      .containing(nodeDependency("husky"))
      .containing(nodeDependency("lint-staged"))
      .containing(nodeDependency("prettier"))
      .containing(nodeDependency("prettier-plugin-java"))
      .containing(nodeDependency("prettier-plugin-packagejson"))
      .containing("\"prepare\": \"husky install\"")
      .containing("\"prettier:check\": \"prettier --check \\\"{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}\\\"\"")
      .containing("\"prettier:format\": \"prettier --write \\\"{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}\\\"\"");
  }

  private String nodeDependency(String packageName) {
    return "\"" + packageName + "\": \"";
  }

  @Test
  void shouldBuildMinimalModule() {
    String folder = TestFileUtils.tmpDirForTest();
    JHipsterModuleProperties properties = properties(folder);

    JHipsterModule module = factory.buildMinimalModule(properties);

    assertMinimalModule(folder, module);
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

  private JHipsterModuleAsserter assertMinimalModule(String folder, JHipsterModule module) {
    JHipsterModuleAsserter asserter = assertThatModule(module)
      .hasFile("README.md")
      .containing("# Test Project")
      .and()
      .hasFiles(".gitignore", ".gitattributes")
      .hasFile(".editorconfig")
      .containing("end_of_line = lf")
      .containing("indent_size = 2")
      .and()
      .hasFiles(".eslintignore");

    verify(git).init(new JHipsterProjectFolder(folder));

    return asserter;
  }
}
