package com.seed4j.shared.projectfolder.infrastructure.primary;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.seed4j.IntegrationTest;
import java.nio.file.FileSystems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@IntegrationTest(properties = "seed4j.forced-project-folder=/tmp/forced")
class ForcedProjectFolderPrefixIT {

  private static final String SEPARATOR = FileSystems.getDefault().getSeparator();

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldHandleProjectWithValidProjectFolder() throws Exception {
    mockMvc
      .perform(
        post("/api/modules/init/apply-patch")
          .contentType(MediaType.APPLICATION_JSON)
          .content(
            """
              {
                "projectFolder": "/tmp/forced/my-project"
              }
            """
          )
      )
      .andExpect(status().isOk());
  }

  @Test
  void shouldNotHandleProjectWithWrongProjectFolder() throws Exception {
    mockMvc
      .perform(
        post("/api/modules/init/apply-patch")
          .contentType(MediaType.APPLICATION_JSON)
          .content(
            """
              {
                "projectFolder": "/home/my-project"
              }
            """
          )
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGetProjectFolder() throws Exception {
    mockMvc.perform(get("/api/project-folders")).andExpect(content().string(containsString(SEPARATOR + "tmp" + SEPARATOR + "forced")));
  }
}
