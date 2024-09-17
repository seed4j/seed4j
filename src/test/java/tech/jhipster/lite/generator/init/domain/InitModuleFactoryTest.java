package tech.jhipster.lite.generator.init.domain;

import static org.mockito.Mockito.when;
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
import tech.jhipster.lite.module.domain.npm.NpmPackageVersion;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class InitModuleFactoryTest {

  @Mock
  private NpmVersions npmVersions;

  @InjectMocks
  private InitModuleFactory factory;

  @Test
  void shouldBuildModule() {
    String folder = TestFileUtils.tmpDirForTest();
    JHipsterModuleProperties properties = properties(folder);
    when(npmVersions.nodeVersion()).thenReturn(new NpmPackageVersion("16.0.0"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("README.md")
      .containing("# Test Project")
      .and()
      .hasFiles(".gitignore", ".gitattributes")
      .hasFile(".editorconfig")
      .containing("end_of_line = crlf")
      .containing("indent_size = 4")
      .and()
      .hasFile("package.json")
      .containing("test-project")
      .containing("Test Project")
      .containing("\"node\": \">=16\"")
      .containing(nodeDependency("husky"))
      .containing(nodeDependency("lint-staged"))
      .containing(nodeScript("prepare", "husky"))
      .and()
      .hasFile(".lintstagedrc.cjs")
      .and()
      .hasExecutableFiles(".husky/pre-commit")
      .hasFile(".npmrc");
  }

  private JHipsterModuleProperties properties(String folder) {
    return JHipsterModulesFixture.propertiesBuilder(folder)
      .projectBaseName("testProject")
      .put("projectName", "Test Project")
      .put("endOfLine", "crlf")
      .put("indentSize", 4)
      .build();
  }
}
