package tech.jhipster.lite.generator.server.javatool.frontendmaven.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/developer-tools/frontend-maven-plugin")
@Tag(name = "Frontend Maven Plugin")
class FrontendMavenResource {

  private final FrontendMavenApplicationService frontendMavenApplicationService;

  public FrontendMavenResource(FrontendMavenApplicationService frontendMavenApplicationService) {
    this.frontendMavenApplicationService = frontendMavenApplicationService;
  }

  @Operation(summary = "Init", description = "Add Frontend Maven Plugin")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Frontend Maven Plugin")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.FRONTEND_MAVEN_PULGIN)
  public void addFrontendMavenPlugin(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    frontendMavenApplicationService.addFrontendMavenPlugin(project);
  }
}
