package tech.jhipster.lite.generator.packagemanager.npm.infrastructure.primary.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.convertObjectToJsonBytes;
import static tech.jhipster.lite.TestUtils.readFileToObject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmRepository;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class NpmResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @SpyBean
  NpmRepository npmRepository;

  @Test
  void shouldInstall() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/package-managers/npm/install").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFileExist(project, "node_modules");
  }

  @Test
  void shouldPrettify() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/package-managers/npm/prettier-format")
          .contentType(MediaType.APPLICATION_JSON)
          .content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    verify(npmRepository).npmPrettierFormat(any(Project.class));
  }
}
