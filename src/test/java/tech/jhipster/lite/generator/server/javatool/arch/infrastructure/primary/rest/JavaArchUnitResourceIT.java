package tech.jhipster.lite.generator.server.javatool.arch.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertArchUnitMavenPlugin;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertFilesAnnotations;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertFilesHexaArchTest;
import static tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitAssertFiles.assertLoggerInConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class JavaArchUnitResourceIT {

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/java/arch").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesAnnotations(project);
    assertFilesHexaArchTest(project);
    assertArchUnitMavenPlugin(project);
    assertLoggerInConfiguration(project);
  }
}
