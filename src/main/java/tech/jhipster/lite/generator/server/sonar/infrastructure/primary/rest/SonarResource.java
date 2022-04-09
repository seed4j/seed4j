package tech.jhipster.lite.generator.server.sonar.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.sonar.application.SonarApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/developer-tools/sonar")
@Tag(name = "Sonar")
class SonarResource {

  private final SonarApplicationService sonarApplicationService;

  public SonarResource(SonarApplicationService sonarApplicationService) {
    this.sonarApplicationService = sonarApplicationService;
  }

  @Operation(summary = "Add Sonar configuration for Java Backend to inspect code quality")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Sonar configuration for Java Backend")
  @PostMapping("/java-backend")
  @GeneratorStep(id = GeneratorAction.SONAR_JAVA_BACKEND)
  public void addSonarJavaBackend(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    sonarApplicationService.addSonarJavaBackend(project);
  }

  @Operation(summary = "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Sonar configuration for Java Backend and Frontend")
  @PostMapping("/java-backend-and-frontend")
  @GeneratorStep(id = GeneratorAction.SONAR_JAVA_BACKEND_AND_FRONTEND)
  public void addSonarJavaBackendAndFrontend(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    sonarApplicationService.addSonarJavaBackendAndFrontend(project);
  }
}
