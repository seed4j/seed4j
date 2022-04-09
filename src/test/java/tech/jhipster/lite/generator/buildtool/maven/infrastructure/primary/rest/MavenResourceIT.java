package tech.jhipster.lite.generator.buildtool.maven.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.buildtool.maven.application.MavenAssertFiles.assertFilesMaven;
import static tech.jhipster.lite.generator.buildtool.maven.application.MavenAssertFiles.assertFilesMavenWrapper;
import static tech.jhipster.lite.generator.buildtool.maven.application.MavenAssertFiles.assertFilesPomXml;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class MavenResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());

    mockMvc
      .perform(
        post("/api/build-tools/maven").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesMaven(project);
    assertFileContent(project, POM_XML, List.of("<groupId>tech.jhipster.chips</groupId>", "<artifactId>chips</artifactId>"));
  }

  @Test
  void shouldAddPomXml() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());

    mockMvc
      .perform(
        post("/api/build-tools/maven/pom-xml")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesPomXml(project);
    assertFileContent(project, POM_XML, List.of("<groupId>tech.jhipster.chips</groupId>", "<artifactId>chips</artifactId>"));
  }

  @Test
  void shouldAddMavenWrapper() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());

    mockMvc
      .perform(
        post("/api/build-tools/maven/wrapper")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesMavenWrapper(project);
  }
}
