package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application.SpringdocAssert.SPRINGDOC_CONFIGURATION_JAVA;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application.SpringdocAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application.SpringdocAssert.assertFileContent;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application.SpringdocAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application.SpringdocAssert.assertJavaFilesWithSecurityJWT;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application.SpringdocAssert.assertProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_API_DESCRIPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_API_TITLE;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_EXT_DOC_DESCRIPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_EXT_DOC_URL;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_LICENSE_NAME;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_LICENSE_URL;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class SpringdocResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    projectDTO.getGeneratorJhipster().put(BASE_NAME, "MyProject");

    Project project = ProjectDTO.toProject(projectDTO);

    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/api-documentations/springdoc/init")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertDependencies(project);
    assertJavaFiles(project);
    assertProperties(project);

    assertFileContent(project, SPRINGDOC_CONFIGURATION_JAVA, "myProject");
    assertFileContent(project, SPRINGDOC_CONFIGURATION_JAVA, DEFAULT_API_TITLE);
    assertFileContent(project, SPRINGDOC_CONFIGURATION_JAVA, DEFAULT_API_DESCRIPTION);
    assertFileContent(project, SPRINGDOC_CONFIGURATION_JAVA, DEFAULT_LICENSE_NAME);
    assertFileContent(project, SPRINGDOC_CONFIGURATION_JAVA, DEFAULT_LICENSE_URL);
    assertFileContent(project, SPRINGDOC_CONFIGURATION_JAVA, DEFAULT_EXT_DOC_DESCRIPTION);
    assertFileContent(project, SPRINGDOC_CONFIGURATION_JAVA, DEFAULT_EXT_DOC_URL);
  }

  @Test
  void shouldInitWithSecurityJWT() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/api-documentations/springdoc/init-with-security-jwt")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertDependencies(project);
    assertJavaFilesWithSecurityJWT(project);
    assertProperties(project);
  }
}
