package tech.jhipster.light.generator.server.springboot.cache.caffeine.infrastructure.primary;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.light.generator.project.domain.Project;
import tech.jhipster.light.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.light.generator.server.springboot.cache.caffeine.application.CaffeineApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot/cache")
@Api(tags = "Spring Boot")
class CaffeineResource {

  private final CaffeineApplicationService caffeineApplicationService;

  public CaffeineResource(CaffeineApplicationService caffeineApplicationService) {
    this.caffeineApplicationService = caffeineApplicationService;
  }

  @ApiOperation("Init the Spring cache configuration")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping("/init")
  public void initSpringCache(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    caffeineApplicationService.initCache(project);
  }
}
