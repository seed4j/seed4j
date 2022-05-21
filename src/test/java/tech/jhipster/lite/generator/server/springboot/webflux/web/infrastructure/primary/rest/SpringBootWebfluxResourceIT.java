package tech.jhipster.lite.generator.server.springboot.webflux.web.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertActuatorDependencies;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertActuatorProperties;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertExceptionHandlerDependencies;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertExceptionHandlerFiles;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertExceptionHandlerProperties;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.assertProperties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
class SpringBootWebfluxResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void addSpringBootWebflux() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/reactive-servers/netty")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertDependencies(project);
    assertProperties(project);
    assertDependencies(project);
    assertProperties(project);
    assertExceptionHandlerDependencies(project);
    assertExceptionHandlerProperties(project);
    assertExceptionHandlerFiles(project);
    assertActuatorDependencies(project);
    assertActuatorProperties(project);
  }
}
