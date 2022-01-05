package tech.jhipster.lite.generator.packagemanager.npm.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.CommandRepository;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class NpmApplicationServiceIT {

  @Autowired
  NpmApplicationService npmApplicationService;

  @SpyBean
  CommandRepository commandRepository;

  @Test
  void shouldAddDependencyWhenNoDependencyEntry() {
    Project project = tmpProjectWithPackageJsonNothing();
    String dependency = "husky";
    String version = "7.0.4";

    npmApplicationService.addDependency(project, dependency, version);

    assertFileContent(project, PACKAGE_JSON, List.of("\"dependencies\": {", "\"husky\": \"7.0.4\"", "},", "\"version\""));
  }

  @Test
  void shouldAddDependencyWhenDependencyEmpty() {
    Project project = tmpProjectWithPackageJsonEmpty();
    String dependency = "husky";
    String version = "7.0.4";

    npmApplicationService.addDependency(project, dependency, version);

    assertFileContent(project, PACKAGE_JSON, List.of("\"dependencies\": {", "\"husky\": \"7.0.4\""));
    assertFileNoContent(project, PACKAGE_JSON, List.of("\"dependencies\": {", "\"husky\": \"7.0.4\","));
  }

  @Test
  void shouldAddDependency() {
    Project project = tmpProjectWithPackageJsonComplete();
    String dependency = "husky";
    String version = "7.0.4";

    npmApplicationService.addDependency(project, dependency, version);

    assertFileContent(project, PACKAGE_JSON, List.of("\"dependencies\": {", "\"husky\": \"7.0.4\","));
  }

  @Test
  void shouldAddScriptWhenNoScriptEntry() {
    Project project = tmpProjectWithPackageJsonNothing();
    String name = "prepare";
    String cmd = "husky install";

    npmApplicationService.addScript(project, name, cmd);

    assertFileContent(project, PACKAGE_JSON, List.of("\"scripts\": {", "\"prepare\": \"husky install\"", "},", "\"version\""));
  }

  @Test
  void shouldAddScriptWhenScriptEmpty() {
    Project project = tmpProjectWithPackageJsonEmpty();
    String name = "prepare";
    String cmd = "husky install";

    npmApplicationService.addScript(project, name, cmd);

    assertFileContent(project, PACKAGE_JSON, List.of("\"scripts\": {", "\"prepare\": \"husky install\""));
  }

  @Test
  void shouldAddScript() {
    Project project = tmpProjectWithPackageJsonComplete();
    String name = "prepare";
    String cmd = "husky install";

    npmApplicationService.addScript(project, name, cmd);

    assertFileContent(project, PACKAGE_JSON, List.of("\"scripts\": {", "\"prepare\": \"husky install\""));
  }

  @Test
  void shouldNpmInstall() {
    Project project = tmpProjectWithPackageJson();
    npmApplicationService.install(project);

    assertFileExist(project, "node_modules");
  }

  @Test
  void shouldPrettify() {
    Project project = tmpProjectWithPackageJson();
    npmApplicationService.prettify(project);

    verify(commandRepository).npmPrettierFormat(any(Project.class));
  }
}
