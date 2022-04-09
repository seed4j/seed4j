package tech.jhipster.lite.generator.packagemanager.npm.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.packagemanager.npm.application.NpmApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/package-managers/npm")
@Tag(name = "NPM")
class NpmResource {

  private final NpmApplicationService npmApplicationService;

  public NpmResource(NpmApplicationService npmApplicationService) {
    this.npmApplicationService = npmApplicationService;
  }

  @Operation(summary = "Run: npm install")
  @ApiResponse(responseCode = "500", description = "An error occurred while installing project")
  @PostMapping("/install")
  @GeneratorStep(id = GeneratorAction.NPM_INSTALL)
  public void install(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    npmApplicationService.install(project);
  }

  @Operation(summary = "Run: npm run prettier:format")
  @ApiResponse(responseCode = "500", description = "An error occurred while prettifying project")
  @PostMapping("/prettier-format")
  @GeneratorStep(id = GeneratorAction.NPM_PRETTIFY)
  public void prettify(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    npmApplicationService.prettify(project);
  }
}
