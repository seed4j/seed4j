package tech.jhipster.lite.generator.server.springboot.database.mariadb.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.application.MariaDBApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/databases/mariadb")
@Tag(name = "Spring Boot - Database")
class MariaDBResource {

  private final MariaDBApplicationService mariaDBApplicationService;

  public MariaDBResource(MariaDBApplicationService mariaDBApplicationService) {
    this.mariaDBApplicationService = mariaDBApplicationService;
  }

  @Operation(summary = "Add MariaDB drivers and dependencies, with testcontainers")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding MariaDB")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.MARIADB)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    mariaDBApplicationService.init(project);
  }
}
