package tech.jhipster.lite.generator.client.angular.core.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.angular.core.application.AngularApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/angular")
@Tag(name = "Angular")
class AngularResource {

  private final AngularApplicationService angularApplicationService;

  public AngularResource(AngularApplicationService angularApplicationService) {
    this.angularApplicationService = angularApplicationService;
  }

  @Operation(summary = "Add Angular", description = "Add Angular")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Angular")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.ANGULAR)
  public void addAngular(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    angularApplicationService.addAngular(project);
  }

  @Operation(summary = "Add Angular with minimal CSS")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Angular with minimal CSS")
  @PostMapping("/styles")
  @GeneratorStep(id = GeneratorAction.ANGULAR_STYLED)
  public void addStyledAngular(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    angularApplicationService.addStyledAngular(project);
  }

  @Operation(summary = "Add Angular with authentication JWT")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Angular with authentication JWT")
  @PostMapping("/jwt")
  @GeneratorStep(id = "angular-jwt")
  public void addJwtAngular(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    angularApplicationService.addJwtAngular(project);
  }
}
