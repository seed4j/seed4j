package tech.jhipster.lite.shared.projectfolder.infrastructure.primary;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.FileSystems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;

@AutoConfigureMockMvc
@IntegrationTest(properties = { "application.forced-project-folder=/tmp/forced" })
class ForcedProjectFolderPrefixIntTest {

  private static final String SEPARATOR = FileSystems.getDefault().getSeparator();

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldHandleProjectWithValidProjectFolder() throws Exception {
    mockMvc
      .perform(
        post("/api/modules/init/apply-patch")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
            {
              "projectFolder": "/tmp/forced/my-project"
            }
          """)
      )
      .andExpect(status().isOk());
  }

  @Test
  void shouldNotHandleProjectWithWrongProjectFolderPrefix() throws Exception {
    mockMvc
      .perform(
        post("/api/modules/init/apply-patch")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
            {
              "projectFolder": "/home/my-project"
            }
          """)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  void shouldNotHandleProjectWithDoubleDotProjectFolderPrefix() throws Exception {
    mockMvc
      .perform(
        post("/api/modules/init/apply-patch")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
            {
              "projectFolder": "/tmp/forced/../my-project"
            }
          """)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGetProjectFolder() throws Exception {
    mockMvc.perform(get("/api/project-folders")).andExpect(content().string(containsString(SEPARATOR + "tmp" + SEPARATOR + "forced")));
  }
}
