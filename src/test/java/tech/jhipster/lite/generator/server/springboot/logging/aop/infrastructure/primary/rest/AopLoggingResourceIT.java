package tech.jhipster.lite.generator.server.springboot.logging.aop.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class AopLoggingResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/log-tools/aop")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFileContent(
      project,
      POM_XML,
      List.of("<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-starter-aop</artifactId>")
    );
    assertFileExist(project, getPath(MAIN_JAVA, "tech/jhipster/chips/technical/infrastructure/secondary/aop/logging/LoggingAspect.java"));
    assertFileExist(
      project,
      getPath(MAIN_JAVA, "tech/jhipster/chips/technical/infrastructure/secondary/aop/logging/LoggingAspectConfiguration.java")
    );
    assertFileExist(
      project,
      getPath(TEST_JAVA, "tech/jhipster/chips/technical/infrastructure/secondary/aop/logging/LoggingAspectTest.java")
    );

    assertFileContent(project, getPath(MAIN_RESOURCES, "config/application.properties"), "application.aop.logging=false");
    assertFileContent(project, getPath(MAIN_RESOURCES, "config/application-local.properties"), "application.aop.logging=true");
    assertFileContent(project, getPath(TEST_RESOURCES, "config/application.properties"), "application.aop.logging=true");
  }
}
