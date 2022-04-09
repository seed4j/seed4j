package tech.jhipster.lite.generator.client.react.core.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.client.react.core.application.ReactAssert.assertAppWithCss;
import static tech.jhipster.lite.generator.client.react.core.application.ReactAssert.assertAppWithoutCss;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.client.react.core.application.ReactAssert;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class ReactResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Test
  void shouldAddReact() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(post("/api/clients/react").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());

    ReactAssert.assertDependency(project);
    ReactAssert.assertScripts(project);
    ReactAssert.assertReactFiles(project);
    ReactAssert.assertFiles(project);
    assertAppWithoutCss(project);
  }

  @Test
  void shouldAddStyledReact() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/clients/react/styles").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    ReactAssert.assertDependency(project);
    ReactAssert.assertScripts(project);
    ReactAssert.assertReactFiles(project);
    ReactAssert.assertFiles(project);
    assertAppWithCss(project);
  }
}
