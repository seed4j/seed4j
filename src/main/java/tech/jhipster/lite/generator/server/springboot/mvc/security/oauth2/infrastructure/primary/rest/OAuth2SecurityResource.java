package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/security-systems")
@Tag(name = "Spring Boot - MVC - Security")
class OAuth2SecurityResource {

  private final OAuth2SecurityApplicationService oauth2SecurityApplicationService;

  public OAuth2SecurityResource(OAuth2SecurityApplicationService oauth2SecurityApplicationService) {
    this.oauth2SecurityApplicationService = oauth2SecurityApplicationService;
  }

  @Operation(summary = "Add a Spring Security: OAuth 2.0 / OIDC Authentication (stateful, works with Keycloak and Okta)")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding a Spring Security OAuth2 / OIDC Authentication")
  @PostMapping("/oauth2")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_OAUTH2)
  public void addOAuth2(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    oauth2SecurityApplicationService.addOAuth2(project);
  }

  @Operation(summary = "Add a account context for OAuth 2.0 / OIDC Authentication")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding account context for OAuth2 / OIDC Authentication")
  @PostMapping("/oauth2/account")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_OAUTH2_ACCOUNT)
  public void addAccountContext(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    oauth2SecurityApplicationService.addAccountContext(project);
  }
}
