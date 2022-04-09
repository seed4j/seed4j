package tech.jhipster.lite.generator.server.javatool.arch.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/java/arch")
@Tag(name = "Java")
class JavaArchUnitResource {

  private final JavaArchUnitApplicationService javaArchUnitApplicationService;

  public JavaArchUnitResource(JavaArchUnitApplicationService javaArchUnitApplicationService) {
    this.javaArchUnitApplicationService = javaArchUnitApplicationService;
  }

  @Operation(summary = "Add Hexagonal Arch classes to project")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Hexagonal Arch classes to project")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.JAVA_ARCHUNIT)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    javaArchUnitApplicationService.init(project);
  }
}
