package tech.jhipster.lite.generator.server.springboot.mvc.web.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterActuatorDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterUndertowDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterWebDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.springBootStarterWebWithoutTomcat;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;

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
class SpringBootMvcResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddSpringBootMvc() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/web-servers/tomcat")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, POM_XML);
    assertFileContent(
      projectPath,
      POM_XML,
      List.of("<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-starter-web</artifactId>")
    );
    assertFileContent(project, POM_XML, springBootStarterWebDependency());

    assertFileContent(projectPath, "src/main/resources/config/application.properties", "server.port=8080");
  }

  @Test
  void shouldAddSpringBootUndertow() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/web-servers/undertow")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, POM_XML);
    assertFileContent(project, POM_XML, springBootStarterWebWithoutTomcat());
    assertFileContent(project, POM_XML, springBootStarterUndertowDependency());

    assertFileContent(projectPath, "src/main/resources/config/application.properties", "server.port=8080");
  }

  @Test
  void shouldAddSpringBootActuator() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/technical-tools/actuator")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, POM_XML);
    assertFileContent(project, POM_XML, springBootStarterActuatorDependency());

    assertFileContent(projectPath, "src/main/resources/config/application.properties", "management.endpoints.web.base-path=/management");
    assertFileContent(
      projectPath,
      "src/main/resources/config/application.properties",
      "management.endpoints.web.exposure.include=configprops, env, health, info, logfile, loggers, threaddump"
    );
    assertFileContent(projectPath, "src/main/resources/config/application.properties", "management.endpoint.health.probes.enabled=true");
    assertFileContent(
      projectPath,
      "src/main/resources/config/application.properties",
      "management.endpoint.health.group.liveness.include=livenessState"
    );
    assertFileContent(
      projectPath,
      "src/main/resources/config/application.properties",
      "management.endpoint.health.group.readiness.include=readinessState"
    );
  }
}
