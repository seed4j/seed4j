package tech.jhipster.lite.generator.server.springboot.database.mongodb.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.application.MongodbApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/databases/mongodb")
@Tag(name = "Spring Boot - Database")
class MongodbResource {

  private final MongodbApplicationService mongodbApplicationService;

  public MongodbResource(MongodbApplicationService mongodbApplicationService) {
    this.mongodbApplicationService = mongodbApplicationService;
  }

  @Operation(summary = "Add MongoDB drivers and dependencies, with testcontainers")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding MongoDB")
  @PostMapping
  @GeneratorStep(id = "mongodb")
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    mongodbApplicationService.init(project);
  }
}
