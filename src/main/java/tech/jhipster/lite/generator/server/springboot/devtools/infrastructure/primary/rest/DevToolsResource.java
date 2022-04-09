package tech.jhipster.lite.generator.server.springboot.devtools.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.devtools.application.DevToolsApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/technical-tools/devtools")
@Tag(name = "Spring Boot - Tools")
class DevToolsResource {

  private final DevToolsApplicationService devToolsApplicationService;

  public DevToolsResource(DevToolsApplicationService devToolsApplicationService) {
    this.devToolsApplicationService = devToolsApplicationService;
  }

  @Operation(summary = "Add Developer Tools dependencies")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Developer Tools")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.DEV_TOOLS)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    devToolsApplicationService.init(project);
  }
}
