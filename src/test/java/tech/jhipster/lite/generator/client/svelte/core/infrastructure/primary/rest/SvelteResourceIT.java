package tech.jhipster.lite.generator.client.svelte.core.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertAppFiles;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertAssets;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertDependency;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertRootFiles;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertScripts;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertSvelteConfigFiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class SvelteResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Test
  void shouldAddSvelte() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(post("/api/clients/svelte").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());

    assertDependency(project);
    assertScripts(project);

    assertSvelteConfigFiles(project);
    assertRootFiles(project);
    assertAppFiles(project);
  }

  @Test
  void shouldAddStyledSvelte() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/clients/svelte/styles").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertDependency(project);
    assertScripts(project);

    assertSvelteConfigFiles(project);
    assertRootFiles(project);
    assertAppFiles(project);
    assertAssets(project);
  }
}
