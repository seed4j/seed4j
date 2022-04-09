package tech.jhipster.lite.generator.server.springboot.mvc.dummy.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.server.springboot.mvc.dummy.application.DummyAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.dummy.application.DummyAssert.assertTestFiles;

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
class DummyResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddDummyContext() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/dummy")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertJavaFiles(project, "tech/jhipster/chips", "tech.jhipster.chips");
    assertTestFiles(project, "tech/jhipster/chips", "tech.jhipster.chips");
  }
}
