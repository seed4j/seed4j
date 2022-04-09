package tech.jhipster.lite.generator.server.springboot.banner.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class BannerResourceIT {

  @Autowired
  MockMvc mockMvc;

  @ParameterizedTest
  @ValueSource(
    strings = {
      "/api/servers/spring-boot/banners/jhipster-v7",
      "/api/servers/spring-boot/banners/ippon",
      "/api/servers/spring-boot/banners/jhipster-v7-react",
      "/api/servers/spring-boot/banners/jhipster-v7-vue",
      "/api/servers/spring-boot/banners/jhipster-v2",
      "/api/servers/spring-boot/banners/jhipster-v3",
    }
  )
  void shouldAddBanner(String url) throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());

    mockMvc
      .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFileExist(project, getPath(MAIN_RESOURCES, "banner.txt"));
  }
}
