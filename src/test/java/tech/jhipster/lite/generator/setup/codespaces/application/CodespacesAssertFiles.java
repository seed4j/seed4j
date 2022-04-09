package tech.jhipster.lite.generator.setup.codespaces.application;

import static tech.jhipster.lite.TestUtils.assertFileExist;

import tech.jhipster.lite.generator.tools.domain.Project;

public class CodespacesAssertFiles {

  private CodespacesAssertFiles() {}

  public static void assertFilesContainerJson(Project project) {
    assertFileExist(project, ".devcontainer/devcontainer.json");
  }

  public static void assertFilesDockerfile(Project project) {
    assertFileExist(project, ".devcontainer/Dockerfile");
  }
}
