package tech.jhipster.lite.generator.history.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.convertObjectToJsonBytes;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class GeneratorHistoryInterceptorIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddInHistory() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());

    mockMvc
      .perform(post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());
    // Then
    String content = FileUtils.read(getPath(projectDTO.getFolder(), ".jhipster", "history.json"));
    assertThat(content).isEqualTo(getExpectedHistoryFileContent());
  }

  private String getExpectedHistoryFileContent() {
    return """
      {
        "values" : [ {
          "serviceId" : "init"
        } ]
      }""";
  }
}
