package tech.jhipster.lite.generator.server.springboot.core.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
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
class SpringBootResourceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddSpringBoot() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    projectApplicationService.init(project);
    mavenApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, POM_XML);
    assertFileContent(projectPath, POM_XML, List.of("<groupId>tech.jhipster.chips</groupId>", "<artifactId>chips</artifactId>"));
    assertFileContent(
      projectPath,
      POM_XML,
      List.of("<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-starter</artifactId>")
    );

    assertFileExist(project, "src/main/java/tech/jhipster/chips/ChipsApp.java");
    assertFileExist(project, "src/test/java/tech/jhipster/chips/ChipsAppTest.java");

    assertFileExist(project, "src/main/resources/config/application.properties");
  }
}
