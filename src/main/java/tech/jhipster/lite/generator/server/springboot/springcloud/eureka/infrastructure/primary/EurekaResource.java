package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/spring-cloud/eureka-client")
@Tag(name = "Spring Boot - Spring Cloud")
class EurekaResource {

  private final EurekaApplicationService eurekaApplicationService;

  public EurekaResource(EurekaApplicationService eurekaApplicationService) {
    this.eurekaApplicationService = eurekaApplicationService;
  }

  @Operation(summary = "Add Spring Cloud Eureka Client")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Cloud Eureka Client")
  @PostMapping
  @GeneratorStep(id = "eureka-client")
  public void init(@RequestBody ProjectDTO projectDTO) {
    eurekaApplicationService.init(ProjectDTO.toProject(projectDTO));
  }
}
