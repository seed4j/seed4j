package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application.SpringdocApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/api-documentations/springdoc")
class SpringdocResource {

  private final SpringdocApplicationService springdocApplicationService;

  public SpringdocResource(SpringdocApplicationService springdocApplicationService) {
    this.springdocApplicationService = springdocApplicationService;
  }

  @Operation(summary = "Add springdoc-openapi")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding springdoc-openapi")
  @PostMapping("/init")
  @GeneratorStep(id = GeneratorAction.SPRINGDOC_OPENAPI)
  @Tag(name = "Spring Boot - MVC")
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    this.springdocApplicationService.init(project);
  }

  @Operation(summary = "Add springdoc-openapi with Security JWT")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding springdoc-openapi with Security JWT")
  @PostMapping("/init-with-security-jwt")
  @GeneratorStep(id = GeneratorAction.SPRINGDOC_OPENAPI_WITH_SECURIITY_JWT)
  @Tag(name = "Spring Boot - MVC - Security")
  public void initWithSecurityJWT(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    this.springdocApplicationService.initWithSecurityJWT(project);
  }
}
