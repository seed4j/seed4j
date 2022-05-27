package tech.jhipster.lite.generator.project.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.convertObjectToJsonBytes;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
class ProjectResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldDownload() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    initApplicationService.init(project);

    mockMvc
      .perform(post("/api/projects/download").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk())
      .andExpect(header().string("Content-Disposition", "attachment; filename=" + project.getBaseName().orElse("application") + ".zip"))
      .andExpect(header().string("X-Suggested-Filename", project.getBaseName().orElse("application") + ".zip"))
      .andExpect(content().contentType("application/octet-stream"));
  }

  @Test
  void shouldGetProjectDetails() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        get("/api/projects/details?folder=" + project.getFolder())
          .contentType(MediaType.APPLICATION_JSON)
          .content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.folder").value(projectDTO.getFolder()))
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.generator-jhipster.projectName").value(projectDTO.getGeneratorJhipster().get(PROJECT_NAME))
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.generator-jhipster.baseName").value(projectDTO.getGeneratorJhipster().get(BASE_NAME)))
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.generator-jhipster.packageName").value(projectDTO.getGeneratorJhipster().get(PACKAGE_NAME))
      );
  }
}
