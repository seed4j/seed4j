package tech.jhipster.lite.generator.history.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.history.application.GeneratorHistoryApplicationService;
import tech.jhipster.lite.generator.history.infrastructure.primary.dto.HistoryDTO;
import tech.jhipster.lite.generator.tools.domain.Project;

@RestController
@RequestMapping("/api/project-histories")
@Tag(name = "History")
class HistoryResource {

  private final GeneratorHistoryApplicationService generatorHistoryApplicationService;

  public HistoryResource(GeneratorHistoryApplicationService generatorHistoryApplicationService) {
    this.generatorHistoryApplicationService = generatorHistoryApplicationService;
  }

  @Operation(summary = "Get project history")
  @ApiResponse(responseCode = "500", description = "An error occurred while getting history project")
  @GetMapping
  public ResponseEntity<HistoryDTO> get(
    @Parameter(description = "Project path to get history") @RequestParam(value = "folder") String folder
  ) {
    Project project = Project.builder().folder(folder).build();
    return ResponseEntity.ok(HistoryDTO.from(generatorHistoryApplicationService.getValues(project)));
  }
}
