package tech.jhipster.lite.generator.server.springboot.core.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot")
@Tag(name = "Spring Boot")
class SpringBootResource {

  private final SpringBootApplicationService springBootApplicationService;

  public SpringBootResource(SpringBootApplicationService springBootApplicationService) {
    this.springBootApplicationService = springBootApplicationService;
  }

  @Operation(summary = "Init Spring Boot project with dependencies, App, and properties")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Boot")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootApplicationService.init(project);
  }
}
