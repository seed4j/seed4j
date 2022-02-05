package tech.jhipster.lite.generator.client.react.core.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.react.core.application.ReactApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/react")
@Tag(name = "React")
class ReactResource {

  private final ReactApplicationService reactApplicationService;

  public ReactResource(ReactApplicationService reactApplicationService) {
    this.reactApplicationService = reactApplicationService;
  }

  @Operation(summary = "Init React", description = "Init React project")
  @ApiResponse(responseCode = "500", description = "An error occurred while initializing React project")
  @PostMapping
  @GeneratorStep(id = "react")
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    reactApplicationService.init(project);
  }
}
