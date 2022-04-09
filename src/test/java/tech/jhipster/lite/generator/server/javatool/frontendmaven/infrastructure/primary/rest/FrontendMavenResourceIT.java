package tech.jhipster.lite.generator.server.javatool.frontendmaven.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenAssert.assertChecksumMavenPlugin;
import static tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenAssert.assertFrontendMavenPlugin;
import static tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenAssert.assertMavenAntrunPlugin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class FrontendMavenResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddFrontendMavenPlugin() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/developer-tools/frontend-maven-plugin")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertChecksumMavenPlugin(project);
    assertMavenAntrunPlugin(project);
    assertFrontendMavenPlugin(project);
  }
}
