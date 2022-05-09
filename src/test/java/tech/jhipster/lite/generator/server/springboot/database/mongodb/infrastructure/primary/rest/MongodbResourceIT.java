package tech.jhipster.lite.generator.server.springboot.database.mongodb.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertConfigFiles;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertDependencies;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertDockerMongodb;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertLoggerInConfig;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.assertTestFiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
class MongodbResourceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  SpringBootMvcApplicationService springBootMvcApplicationService;

  @Autowired
  OAuth2SecurityApplicationService oAuth2SecurityApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/databases/mongodb")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertDependencies(project);
    assertDockerMongodb(project);
    assertJavaFiles(project, false);
    assertTestFiles(project);
    assertConfigFiles(project);
    assertLoggerInConfig(project);
  }

  @Test
  void shouldInitWithSpringSecurity() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    springBootMvcApplicationService.addExceptionHandler(project);
    oAuth2SecurityApplicationService.addOAuth2(project);
    oAuth2SecurityApplicationService.addAccountContext(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/databases/mongodb")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertDependencies(project);
    assertDockerMongodb(project);
    assertJavaFiles(project, true);
    assertTestFiles(project);
    assertConfigFiles(project);
    assertLoggerInConfig(project);
  }
}
