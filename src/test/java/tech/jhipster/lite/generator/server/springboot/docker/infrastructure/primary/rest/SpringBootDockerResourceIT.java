package tech.jhipster.lite.generator.server.springboot.docker.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileExist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.docker.application.SpringBootDockerApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class SpringBootDockerResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootDockerApplicationService springBootDockerApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddJib() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootDockerApplicationService.addJib(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/containers/docker/jib")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();

    assertFileExist(projectPath, "src/main/docker/jib/entrypoint.sh");
  }

  @Test
  void shouldAddDockerfile() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/containers/docker/dockerfile")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();

    assertFileExist(projectPath, "Dockerfile");
  }
}
