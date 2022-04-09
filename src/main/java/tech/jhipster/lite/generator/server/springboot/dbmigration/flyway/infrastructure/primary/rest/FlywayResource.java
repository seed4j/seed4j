package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/database-migration-tools/flyway")
@Tag(name = "Spring Boot - Database Migration")
class FlywayResource {

  private final FlywayApplicationService flywayApplicationService;

  public FlywayResource(FlywayApplicationService flywayApplicationService) {
    this.flywayApplicationService = flywayApplicationService;
  }

  @Operation(summary = "Add Flyway")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Flyway")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.FLYWAY)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    flywayApplicationService.init(project);
  }

  @Operation(summary = "Add User and Authority changelogs")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding changelogs for user and authority")
  @PostMapping("/user")
  @GeneratorStep(id = GeneratorAction.FLYWAY_USER_AND_AUTHORITY_CHANGELOGS)
  public void addUserAndAuthority(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    flywayApplicationService.addUserAuthorityChangelog(project);
  }
}
