package tech.jhipster.lite.generator.ci.github.actions.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.convertObjectToJsonBytes;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.generator.ci.github.actions.application.GitHubActionsAssertFiles.assertFilesGitHubActions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.ci.github.actions.application.GitHubActionsApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class GitHubActionsResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  GitHubActionsApplicationService gitHubActionsApplicationService;

  @Test
  void shouldAddGitHubActionsForMaven() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());

    mockMvc
      .perform(
        post("/api/developer-tools/github-actions/maven")
          .contentType(MediaType.APPLICATION_JSON)
          .content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesGitHubActions(project);
  }
}
