package tech.jhipster.lite.generator.server.springboot.mvc.dummy.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.application.DummyApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/features/dummy")
@Tag(name = "Spring Boot - MVC")
class DummyResource {

  private final DummyApplicationService dummyApplicationService;

  public DummyResource(DummyApplicationService dummyApplicationService) {
    this.dummyApplicationService = dummyApplicationService;
  }

  @Operation(summary = "Add Dummy context with some APIs")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Dummy context")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.DUMMY_CONTEXT)
  public void addDummyContext(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    dummyApplicationService.applyDummyGitPatch(project);
  }
}
