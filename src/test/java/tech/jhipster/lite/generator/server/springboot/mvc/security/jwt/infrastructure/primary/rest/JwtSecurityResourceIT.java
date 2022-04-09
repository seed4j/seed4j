package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertBasicAuthJavaFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertBasicAuthProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertJwtSecurityFilesExists;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertJwtSecurityProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertLoggerInConfiguration;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.assertPomXmlProperties;

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
import tech.jhipster.lite.generator.server.javatool.base.application.JavaBaseApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.tools.infrastructure.secondary.GitUtils;

@IntegrationTest
@AutoConfigureMockMvc
class JwtSecurityResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  JavaBaseApplicationService javaBaseApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  SpringBootMvcApplicationService springBootMvcApplicationService;

  @Autowired
  JwtSecurityApplicationService jwtSecurityApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    GitUtils.init(project.getFolder());
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    javaBaseApplicationService.addJavaBase(project);
    springBootApplicationService.init(project);
    springBootMvcApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/security-systems/jwt")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertPomXmlProperties(project);
    assertJwtSecurityFilesExists(project);
    assertJwtSecurityProperties(project);
    assertLoggerInConfiguration(project);
  }

  @Test
  void shouldAddBasicAuth() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    GitUtils.init(project.getFolder());
    projectApplicationService.init(project);
    mavenApplicationService.init(project);
    javaBaseApplicationService.addJavaBase(project);
    springBootApplicationService.init(project);
    springBootMvcApplicationService.init(project);
    jwtSecurityApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/security-systems/jwt/basic-auth")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertBasicAuthJavaFiles(project);
    assertBasicAuthProperties(project);
  }
}
