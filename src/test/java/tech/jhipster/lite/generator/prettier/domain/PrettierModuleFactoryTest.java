package tech.jhipster.lite.generator.prettier.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PrettierModuleFactoryTest {

  @InjectMocks
  private PrettierModuleFactory factory;

  @Test
  void shouldBuildModule() {
    String folder = TestFileUtils.tmpDirForTest();
    JHipsterModuleProperties properties = properties(folder);

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFiles(".lintstagedrc.js", ".prettierignore")
      .hasFile(".prettierrc")
      .containing("tabWidth: 4")
      .containing("endOfLine: \"crlf\"")
      .and()
      .hasExecutableFiles(".husky/pre-commit")
      .hasFile("package.json")
      .containing(nodeDependency("@prettier/plugin-xml"))
      .containing(nodeDependency("husky"))
      .containing(nodeDependency("lint-staged"))
      .containing(nodeDependency("prettier"))
      .containing(nodeDependency("prettier-plugin-java"))
      .containing(nodeDependency("prettier-plugin-packagejson"))
      .containing(nodeScript("prepare", "husky install"))
      .containing(nodeScript("prettier:check", "prettier --check '{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}'"))
      .containing(nodeScript("prettier:format", "prettier --write '{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}'"));
  }

  private JHipsterModuleProperties properties(String folder) {
    return JHipsterModulesFixture
      .propertiesBuilder(folder)
      .projectBaseName("testProject")
      .put("projectName", "Test Project")
      .put("indentSize", 4)
      .put("endOfLine", "crlf")
      .build();
  }
}
