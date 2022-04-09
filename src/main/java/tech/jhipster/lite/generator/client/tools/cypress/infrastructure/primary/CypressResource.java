package tech.jhipster.lite.generator.client.tools.cypress.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.tools.cypress.application.CypressApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/cypress")
@Tag(name = "E2e")
class CypressResource {

  private final CypressApplicationService cypressApplicationService;

  public CypressResource(CypressApplicationService cypressApplicationService) {
    this.cypressApplicationService = cypressApplicationService;
  }

  @Operation(summary = "Add Cypress", description = "Add Cypress")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding cypress")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.REACT_CYPRESS)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    cypressApplicationService.init(project);
  }
}
