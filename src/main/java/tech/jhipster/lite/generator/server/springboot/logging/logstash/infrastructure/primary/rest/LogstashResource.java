package tech.jhipster.lite.generator.server.springboot.logging.logstash.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.logging.logstash.application.LogstashApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/log-tools/logstash")
@Tag(name = "Spring Boot - Logging")
class LogstashResource {

  private final LogstashApplicationService logstashApplicationService;

  public LogstashResource(LogstashApplicationService logstashApplicationService) {
    this.logstashApplicationService = logstashApplicationService;
  }

  @Operation(summary = "Add Logstash TCP appender")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Logstash TCP appender")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.LOGSTASH)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    logstashApplicationService.init(project);
  }
}
