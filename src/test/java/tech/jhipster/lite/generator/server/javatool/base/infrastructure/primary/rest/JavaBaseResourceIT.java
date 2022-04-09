package tech.jhipster.lite.generator.server.javatool.base.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.server.javatool.base.domain.JavaBaseDomainService.ERROR_DOMAIN_PATH;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.server.javatool.base.domain.JavaBase;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class JavaBaseResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddJavaBase() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mockMvc
      .perform(
        post("/api/servers/java/base").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String specificPath = "tech/jhipster/chips";

    String pathMain = getPath(MAIN_JAVA, specificPath, ERROR_DOMAIN_PATH);
    JavaBase.errorDomainFiles().forEach(file -> assertFileExist(project, getPath(pathMain, file)));

    String pathTest = getPath(TEST_JAVA, specificPath, ERROR_DOMAIN_PATH);
    JavaBase.errorDomainTestFiles().forEach(file -> assertFileExist(project, getPath(pathTest, file)));

    JavaBase.annotationsFiles().forEach(file -> assertFileExist(project, getPath(TEST_JAVA, specificPath, file)));
  }
}
