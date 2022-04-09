package tech.jhipster.lite.generator.server.springboot.database.mysql.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/databases/mysql")
@Tag(name = "Spring Boot - Database")
class MySQLResource {

  private final MySQLApplicationService mysqlApplicationService;

  public MySQLResource(MySQLApplicationService mysqlApplicationService) {
    this.mysqlApplicationService = mysqlApplicationService;
  }

  @Operation(summary = "Add MySQL drivers and dependencies, with testcontainers")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding MySQL")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.MYSQL)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    mysqlApplicationService.init(project);
  }
}
