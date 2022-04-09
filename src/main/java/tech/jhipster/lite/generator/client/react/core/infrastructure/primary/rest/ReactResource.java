package tech.jhipster.lite.generator.client.react.core.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.react.core.application.ReactApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/react")
@Tag(name = "React")
class ReactResource {

  private final ReactApplicationService reactApplicationService;

  public ReactResource(ReactApplicationService reactApplicationService) {
    this.reactApplicationService = reactApplicationService;
  }

  @Operation(summary = "Init React+Vite", description = "Init React+Vite project")
  @ApiResponse(responseCode = "500", description = "An error occurred while initializing React+Vite project")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.REACT)
  public void addReact(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    reactApplicationService.addReact(project);
  }

  @Operation(summary = "Add React+Vite with minimal CSS", description = "Add React+Vite with minimal CSS")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding React+Vite with minimal CSS")
  @PostMapping("/styles")
  @GeneratorStep(id = GeneratorAction.REACT_STYLED)
  public void addStyledReact(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    reactApplicationService.addStyledReact(project);
  }
}
