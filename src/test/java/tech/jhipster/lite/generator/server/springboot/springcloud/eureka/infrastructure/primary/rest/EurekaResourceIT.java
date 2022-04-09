package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaAssert.assertDockerCompose;
import static tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaAssert.assertProperties;

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
class EurekaResourceIT {

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
    // Given
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());

    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    // When
    mockMvc
      .perform(
        post("/api/servers/spring-boot/distributed-systems/spring-cloud/eureka-client")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    // Then
    assertDependencies(project);
    assertProperties(project);
    assertDockerCompose(project);
  }
}
