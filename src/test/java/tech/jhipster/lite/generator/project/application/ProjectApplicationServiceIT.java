package tech.jhipster.lite.generator.project.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectBuilder;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;
import static tech.jhipster.lite.common.domain.WordUtils.CRLF;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFileGitInit;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFilesEditorConfiguration;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFilesGitConfiguration;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFilesInit;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFilesPackageJson;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFilesPrettier;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFilesReadme;
import static tech.jhipster.lite.generator.tools.domain.Constants.PACKAGE_JSON;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PRETTIER_DEFAULT_INDENT;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PROJECT_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class ProjectApplicationServiceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Test
  void shouldInitWithConfig() {
    // @formatter:off
    Map<String, Object> config = new HashMap<>(
      Map.of(
        BASE_NAME, "jhipsterLite",
        PROJECT_NAME, "JHipster Lite",
        PRETTIER_DEFAULT_INDENT, 4
      )
    );
    // @formatter:on
    Project project = tmpProjectBuilder().endOfLine(CRLF).config(config).build();

    projectApplicationService.init(project);

    assertFilesInit(project);
    assertFileContent(project, "README.md", "JHipster Lite");
    assertFileContent(project, PACKAGE_JSON, "jhipster-lite");
    assertFileContent(project, ".editorconfig", "end_of_line = crlf");
    assertFileContent(project, ".prettierrc", "endOfLine: \"crlf\"");
    // @formatter:off
    assertFileContent(project, ".prettierrc",
      List.of(
        "overrides:",
        "- files: \"*.java\"",
        "options:",
        "tabWidth: 4"
      )
    );
    // @formatter:on
    assertFileGitInit(project);
  }

  @Test
  void shouldInitWithDefaultConfig() {
    Project project = tmpProject();

    projectApplicationService.init(project);

    assertFilesInit(project);
    assertFileContent(project, "README.md", "JHipster Project");
    assertFileContent(project, PACKAGE_JSON, "jhipster");
    assertFileContent(project, ".editorconfig", "end_of_line = lf");
    assertFileContent(project, ".prettierrc", "endOfLine: \"lf\"");
    // @formatter:off
    assertFileContent(project, ".prettierrc",
      List.of(
        "overrides:",
        "- files: \"*.java\"",
        "options:",
        "tabWidth: 2"
      )
    );
    // @formatter:on
  }

  @Test
  void shouldAddPackageJson() {
    Project project = tmpProject();

    projectApplicationService.addPackageJson(project);

    assertFilesPackageJson(project);
    List
      .of("@prettier/plugin-xml", "husky", "lint-staged", "prettier", "prettier-plugin-java", "prettier-plugin-packagejson")
      .forEach(dependency -> assertFileContent(project, PACKAGE_JSON, dependency));
    List.of("prepare", "prettier:check", "prettier:format").forEach(dependency -> assertFileContent(project, PACKAGE_JSON, dependency));
  }

  @Test
  void shouldAddReadme() {
    Project project = tmpProject();

    projectApplicationService.addReadme(project);

    assertFilesReadme(project);
  }

  @Test
  void shouldAddGitConfiguration() {
    Project project = tmpProject();

    projectApplicationService.addGitConfiguration(project);

    assertFilesGitConfiguration(project);
  }

  @Test
  void shouldAddEditorConfiguration() {
    Project project = tmpProject();

    projectApplicationService.addEditorConfiguration(project);

    assertFilesEditorConfiguration(project);
  }

  @Test
  void shouldAddPrettier() {
    Project project = tmpProject();

    projectApplicationService.addPrettier(project);

    assertFilesPrettier(project);
  }

  @Test
  void shouldGitInit() {
    Project project = tmpProject();

    projectApplicationService.gitInit(project);

    assertFileGitInit(project);
  }

  @Test
  void shouldDownloadProject() {
    Project project = tmpProjectWithPomXml();

    assertThat(projectApplicationService.download(project)).isNotNull();
  }
}
