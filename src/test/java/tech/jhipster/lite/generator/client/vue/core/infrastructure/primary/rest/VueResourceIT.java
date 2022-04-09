package tech.jhipster.lite.generator.client.vue.core.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.client.vue.core.application.VueAssert;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class VueResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Test
  void shouldAddVue() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(post("/api/clients/vue").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());

    VueAssert.assertDependency(project);
    VueAssert.assertScripts(project);

    VueAssert.assertViteConfigFiles(project);
    VueAssert.assertRootFiles(project);
    VueAssert.assertAppFiles(project);
    VueAssert.assertAppWithoutCss(project);

    VueAssert.assertJestSonar(project);
  }

  @Test
  void shouldAddPinia() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(post("/api/clients/vue").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());

    mockMvc
      .perform(
        post("/api/clients/vue/stores/pinia")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());
    VueAssert.assertPiniaDependency(project);
  }

  @Test
  void shouldAddStyledVue() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/clients/vue/styles").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    VueAssert.assertDependency(project);
    VueAssert.assertScripts(project);

    VueAssert.assertViteConfigFiles(project);
    VueAssert.assertRootFiles(project);
    VueAssert.assertAppFiles(project);
    VueAssert.assertAppWithCss(project);

    VueAssert.assertJestSonar(project);
  }
}
