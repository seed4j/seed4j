package tech.jhipster.lite.generator.server.springboot.docker.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.docker.application.SpringBootDockerApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot/docker")
@Tag(name = "Spring Boot - Tools")
class SpringBootDockerResource {

  private final SpringBootDockerApplicationService springBootDockerApplicationService;

  public SpringBootDockerResource(SpringBootDockerApplicationService springBootDockerApplicationService) {
    this.springBootDockerApplicationService = springBootDockerApplicationService;
  }

  @Operation(summary = "Add docker image building with Jib")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding jib")
  @PostMapping("/jib")
  public void addJib(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootDockerApplicationService.addJib(project);
  }

  @Operation(summary = "Add docker file")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding jib")
  @PostMapping("/docker-file")
  public void addDockerFile(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootDockerApplicationService.addDockerFile(project);
  }
}
