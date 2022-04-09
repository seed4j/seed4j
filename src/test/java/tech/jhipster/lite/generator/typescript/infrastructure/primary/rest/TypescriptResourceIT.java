package tech.jhipster.lite.generator.typescript.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;

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
import tech.jhipster.lite.generator.typescript.application.TypescriptAssert;

@IntegrationTest
@AutoConfigureMockMvc
class TypescriptResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/javascript-languages/typescript")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    TypescriptAssert.assertDevDependencies(project);
    TypescriptAssert.assertScripts(project);
    TypescriptAssert.assertConfigFiles(project);
  }
}
