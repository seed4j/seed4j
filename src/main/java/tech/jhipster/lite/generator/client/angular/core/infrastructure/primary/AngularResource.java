package tech.jhipster.lite.generator.client.angular.core.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.angular.core.application.AngularApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/angular")
@Tag(name = "Angular")
class AngularResource {

  private final AngularApplicationService angularApplicationService;

  public AngularResource(AngularApplicationService angularApplicationService) {
    this.angularApplicationService = angularApplicationService;
  }

  @Operation(summary = "Init Angular", description = "Init Angular project")
  @ApiResponse(responseCode = "500", description = "An error occurred while initializing Angular project")
  @PostMapping
  @GeneratorStep(id = "angular")
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    angularApplicationService.init(project);
  }
}
