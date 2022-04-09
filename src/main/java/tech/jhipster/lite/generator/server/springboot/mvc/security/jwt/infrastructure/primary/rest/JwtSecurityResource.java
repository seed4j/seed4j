package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/security-systems")
@Tag(name = "Spring Boot - MVC - Security")
class JwtSecurityResource {

  private final JwtSecurityApplicationService jwtSecurityApplicationService;

  public JwtSecurityResource(JwtSecurityApplicationService jwtSecurityApplicationService) {
    this.jwtSecurityApplicationService = jwtSecurityApplicationService;
  }

  @Operation(summary = "Add Spring Security JWT")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Security JWT")
  @PostMapping("/jwt")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_JWT)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    jwtSecurityApplicationService.init(project);
  }

  @Operation(summary = "Add Basic Auth for Spring Security JWT")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Basic Auth for Spring Security JWT")
  @PostMapping("/jwt/basic-auth")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_JWT_BASIC_AUTH)
  public void addBasicAuth(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    jwtSecurityApplicationService.addBasicAuth(project);
  }
}
