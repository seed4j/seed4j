package tech.jhipster.lite.generator.project.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.convertObjectToJsonBytes;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.generator.project.application.ProjectAssertFiles.assertFilesInit;
import static tech.jhipster.lite.generator.tools.domain.Constants.PACKAGE_JSON;

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
class ProjectResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @SpyBean
  NpmRepository npmRepository;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());

    mockMvc
      .perform(post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesInit(project);
    assertFileContent(project, "README.md", "Chips Project");
    assertFileContent(project, ".prettierrc", "tabWidth: 2");
    assertFileContent(project, PACKAGE_JSON, "chips");
  }

  @Test
  void shouldDownload() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    projectApplicationService.init(project);

    mockMvc
      .perform(post("/api/project-downloads").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk())
      .andExpect(header().string("Content-Disposition", "attachment; filename=" + project.getBaseName().orElse("application") + ".zip"))
      .andExpect(header().string("X-Suggested-Filename", project.getBaseName().orElse("application") + ".zip"))
      .andExpect(content().contentType("application/octet-stream"));
  }
}
