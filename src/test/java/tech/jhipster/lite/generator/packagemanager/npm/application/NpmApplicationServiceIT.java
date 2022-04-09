package tech.jhipster.lite.generator.packagemanager.npm.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.assertFileNoContent;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJson;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonEmpty;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonNothing;
import static tech.jhipster.lite.generator.tools.domain.Constants.PACKAGE_JSON;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmRepository;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class NpmApplicationServiceIT {

  @Autowired
  NpmApplicationService npmApplicationService;

  @SpyBean
  NpmRepository npmRepository;

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
  void shouldAddDevDependencyWhenNoDevDependencyEntry() {
    Project project = tmpProjectWithPackageJsonNothing();
    String dependency = "husky";
    String version = "7.0.4";

    npmApplicationService.addDevDependency(project, dependency, version);

    assertFileContent(project, PACKAGE_JSON, List.of("\"devDependencies\": {", "\"husky\": \"7.0.4\"", "},", "\"version\""));
  }

  @Test
  void shouldAddDevDependencyWhenDevDependencyEmpty() {
    Project project = tmpProjectWithPackageJsonEmpty();
    String dependency = "husky";
    String version = "7.0.4";

    npmApplicationService.addDevDependency(project, dependency, version);

    assertFileContent(project, PACKAGE_JSON, List.of("\"devDependencies\": {", "\"husky\": \"7.0.4\""));
    assertFileNoContent(project, PACKAGE_JSON, List.of("\"devDependencies\": {", "\"husky\": \"7.0.4\","));
  }

  @Test
  void shouldAddDevDependency() {
    Project project = tmpProjectWithPackageJsonComplete();
    String dependency = "husky";
    String version = "7.0.4";

    npmApplicationService.addDevDependency(project, dependency, version);

    assertFileContent(project, PACKAGE_JSON, List.of("\"devDependencies\": {", "\"husky\": \"7.0.4\","));
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

    verify(npmRepository).npmPrettierFormat(any(Project.class));
  }
}
