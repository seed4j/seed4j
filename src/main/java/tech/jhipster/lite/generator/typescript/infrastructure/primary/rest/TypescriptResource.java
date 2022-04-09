package tech.jhipster.lite.generator.typescript.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.typescript.application.TypescriptApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/javascript-languages/typescript")
@Tag(name = "Typescript")
class TypescriptResource {

  private final TypescriptApplicationService typescriptApplicationService;

  public TypescriptResource(TypescriptApplicationService typescriptApplicationService) {
    this.typescriptApplicationService = typescriptApplicationService;
  }

  @Operation(summary = "Init Typescript", description = "Init Typescript project")
  @ApiResponse(responseCode = "500", description = "An error occurred while initializing Typescript project")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.TYPESCRIPT)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    typescriptApplicationService.init(project);
  }
}
