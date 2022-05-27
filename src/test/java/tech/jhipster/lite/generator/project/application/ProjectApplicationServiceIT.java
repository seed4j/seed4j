package tech.jhipster.lite.generator.project.application;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.common.application.SpringBootCommonApplicationService;

@IntegrationTest
class ProjectApplicationServiceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  SpringBootCommonApplicationService springBootCommonApplicationService;

  @Test
  void shouldDownloadProject() {
    Project project = tmpProjectWithPomXml();

    assertThat(projectApplicationService.download(project)).isNotNull();
  }

  @Test
  void shouldGetProjectDetailsForInitProject() {
    Project project = tmpProjectWithPackageJsonComplete();

    Project projectDetails = projectApplicationService.getProjectDetails(project.getFolder());
    assertThat(projectDetails).isNotNull();
    assertThat(projectDetails.getConfig()).isNotNull();
    assertThat(projectDetails.getConfig()).containsEntry(PROJECT_NAME, "Jhipster lite");
  }

  @Test
  void shouldGetProjectDetailsForMavenProject() {
    Project project = tmpProjectWithPackageJsonComplete();
    TestUtils.copyPomXml(project);

    Project projectDetails = projectApplicationService.getProjectDetails(project.getFolder());
    assertThat(projectDetails).isNotNull();
    assertThat(projectDetails.getConfig()).isNotNull();
    assertThat(projectDetails.getConfig()).containsEntry(PROJECT_NAME, "Jhipster lite");
    assertThat(projectDetails.getConfig()).containsEntry(BASE_NAME, "jhipster");
    assertThat(projectDetails.getConfig()).containsEntry(PACKAGE_NAME, "com.mycompany.myapp");
  }

  @Test
  void shouldGetProjectDetailsForGradleProject() {
    Project project = tmpProjectWithPackageJsonComplete();
    TestUtils.copyPomXml(project);

    Project projectDetails = projectApplicationService.getProjectDetails(project.getFolder());
    assertThat(projectDetails).isNotNull();
    assertThat(projectDetails.getConfig()).isNotNull();
    assertThat(projectDetails.getConfig()).containsEntry(PROJECT_NAME, "Jhipster lite");
    assertThat(projectDetails.getConfig()).containsEntry(PACKAGE_NAME, "com.mycompany.myapp");
  }

  @Test
  void shouldGetProjectDetailsForMavenMvcProject() {
    Project project = tmpProjectWithPackageJsonComplete();
    TestUtils.copyPomXml(project);
    TestUtils.copySpringBootProperties(project);
    springBootCommonApplicationService.addProperties(project, "server.port", 8084);

    Project projectDetails = projectApplicationService.getProjectDetails(project.getFolder());
    assertThat(projectDetails).isNotNull();
    assertThat(projectDetails.getConfig()).isNotNull();
    assertThat(projectDetails.getConfig()).containsEntry(PROJECT_NAME, "Jhipster lite");
    assertThat(projectDetails.getConfig()).containsEntry(BASE_NAME, "jhipster");
    assertThat(projectDetails.getConfig()).containsEntry(PACKAGE_NAME, "com.mycompany.myapp");
    assertThat(projectDetails.getConfig()).containsEntry("serverPort", 8084);
  }
}
