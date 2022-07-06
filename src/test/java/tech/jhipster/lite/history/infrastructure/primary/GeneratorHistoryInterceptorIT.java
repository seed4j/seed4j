package tech.jhipster.lite.history.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
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
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class GeneratorHistoryInterceptorIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldNotAddHistoryOnUnknownFolder() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());

    mockMvc
      .perform(
        post("/api/servers/spring-boot/databases/mongodb")
          .contentType(MediaType.APPLICATION_JSON)
          .content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isBadRequest());

    assertThat(FileUtils.exists(getPath(projectDTO.getFolder(), ".jhipster/history", "history.json"))).isFalse();
  }
}
