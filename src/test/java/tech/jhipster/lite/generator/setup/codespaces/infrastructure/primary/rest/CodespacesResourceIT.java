package tech.jhipster.lite.generator.setup.codespaces.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.convertObjectToJsonBytes;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.setup.codespaces.application.CodespacesAssertFiles.assertFilesContainerJson;
import static tech.jhipster.lite.generator.setup.codespaces.application.CodespacesAssertFiles.assertFilesDockerfile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class CodespacesResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mockMvc
      .perform(
        post("/api/developer-tools/codespaces").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesDockerfile(project);
    assertFilesContainerJson(project);
    assertFileContent(project, ".devcontainer/devcontainer.json", "\"dockerfile\": \"Dockerfile\"");
    assertFileContent(project, ".devcontainer/Dockerfile", "ARG VARIANT=\"17\"");
  }
}
