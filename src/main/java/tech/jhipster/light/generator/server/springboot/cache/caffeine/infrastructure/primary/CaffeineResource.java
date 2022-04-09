package tech.jhipster.light.generator.server.springboot.cache.caffeine.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.light.generator.project.domain.Project;
import tech.jhipster.light.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.light.generator.server.springboot.cache.caffeine.application.CaffeineApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot/cache")
@Tag(name = "Spring Boot")
class CaffeineResource {

  private final CaffeineApplicationService caffeineApplicationService;

  public CaffeineResource(CaffeineApplicationService caffeineApplicationService) {
    this.caffeineApplicationService = caffeineApplicationService;
  }

  @Operation(summary = "Init the Spring cache configuration")
  @ApiResponses({ @ApiResponse(responseCode = "500", description = "An error occurred while initializing project") })
  @PostMapping("/init")
  public void initSpringCache(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    caffeineApplicationService.initCache(project);
  }
}
