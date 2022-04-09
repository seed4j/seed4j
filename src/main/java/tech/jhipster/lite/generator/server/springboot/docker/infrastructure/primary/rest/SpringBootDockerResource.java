package tech.jhipster.lite.generator.server.springboot.docker.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.docker.application.SpringBootDockerApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/containers/docker")
@Tag(name = "Spring Boot - Tools")
class SpringBootDockerResource {

  private final SpringBootDockerApplicationService springBootDockerApplicationService;

  public SpringBootDockerResource(SpringBootDockerApplicationService springBootDockerApplicationService) {
    this.springBootDockerApplicationService = springBootDockerApplicationService;
  }

  @Operation(summary = "Add Docker image building with Jib")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding jib")
  @PostMapping("/jib")
  @GeneratorStep(id = GeneratorAction.JIB)
  public void addJib(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootDockerApplicationService.addJib(project);
  }

  @Operation(summary = "Add Dockerfile")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Dockerfile")
  @PostMapping("/dockerfile")
  @GeneratorStep(id = GeneratorAction.DOCKERFILE)
  public void addDockerfile(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootDockerApplicationService.addDockerfile(project);
  }
}
