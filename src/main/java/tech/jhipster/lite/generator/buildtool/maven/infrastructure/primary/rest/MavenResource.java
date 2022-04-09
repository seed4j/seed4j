package tech.jhipster.lite.generator.buildtool.maven.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/build-tools/maven")
@Tag(name = "Build Tool")
class MavenResource {

  private final MavenApplicationService mavenApplicationService;

  public MavenResource(MavenApplicationService mavenApplicationService) {
    this.mavenApplicationService = mavenApplicationService;
  }

  @Operation(summary = "Init", description = "Init Maven project with pom.xml and wrapper")
  @ApiResponse(responseCode = "500", description = "An error occurred while initializing Maven project")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.MAVEN_JAVA)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    mavenApplicationService.init(project);
  }

  @Operation(summary = "Add pom.xml")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding pom.xml to project")
  @PostMapping("/pom-xml")
  @GeneratorStep(id = GeneratorAction.MAVEN_JAVA_POM_XML)
  public void addPomXml(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    mavenApplicationService.addPomXml(project);
  }

  @Operation(summary = "Add Maven Wrapper")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Maven Wrapper to project")
  @PostMapping("/wrapper")
  @GeneratorStep(id = GeneratorAction.MAVEN_WRAPPER)
  public void addMavenWrapper(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    mavenApplicationService.addMavenWrapper(project);
  }
}
