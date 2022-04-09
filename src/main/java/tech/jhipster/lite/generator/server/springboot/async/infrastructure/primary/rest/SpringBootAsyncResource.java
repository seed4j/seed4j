package tech.jhipster.lite.generator.server.springboot.async.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.async.application.SpringBootAsyncApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/async")
@Tag(name = "Spring Boot - Tools")
class SpringBootAsyncResource {

  private final SpringBootAsyncApplicationService springBootAsyncApplicationService;

  public SpringBootAsyncResource(SpringBootAsyncApplicationService springBootAsyncApplicationService) {
    this.springBootAsyncApplicationService = springBootAsyncApplicationService;
  }

  @Operation(summary = "Add asynchronous execution and scheduling configuration")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding asynchronous execution and scheduling configuration")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_ASYNC)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootAsyncApplicationService.init(project);
  }
}
