package tech.jhipster.lite.generator.server.javatool.jacoco.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.javatool.jacoco.application.JacocoApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/java/jacoco-minimum-coverage")
@Tag(name = "Java")
class JacocoResource {

  private final JacocoApplicationService jacocoApplicationService;

  public JacocoResource(JacocoApplicationService jacocoApplicationService) {
    this.jacocoApplicationService = jacocoApplicationService;
  }

  @Operation(summary = "Add JaCoCo configuration to check minimum coverage")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding JaCoco configuration")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.JACOCO_CHECK_MIN_COVERAGE)
  public void addCheckMinimumCoverage(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    jacocoApplicationService.addCheckMinimumCoverage(project);
  }
}
