package tech.jhipster.lite.generator.server.springboot.database.cassandra.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.application.CassandraApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/databases/cassandra")
@Tag(name = "Spring Boot - Database")
public class CassandraResource {

  public final CassandraApplicationService cassandraApplicationService;

  public CassandraResource(CassandraApplicationService cassandraApplicationService) {
    this.cassandraApplicationService = cassandraApplicationService;
  }

  @Operation(summary = "Add Cassandra  and dependencies, with testcontainers")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Cassandra")
  @PostMapping
  @GeneratorStep(id = "cassandra")
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    cassandraApplicationService.init(project);
  }
}
