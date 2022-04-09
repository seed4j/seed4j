package tech.jhipster.lite.generator.server.springboot.springcloud.consul.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.application.ConsulApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/distributed-systems/spring-cloud/consul")
@Tag(name = "Spring Boot - Spring Cloud")
class ConsulResource {

  private final ConsulApplicationService consulApplicationService;

  public ConsulResource(ConsulApplicationService consulApplicationService) {
    this.consulApplicationService = consulApplicationService;
  }

  @Operation(summary = "Add Spring Cloud Consul config and discovery")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Cloud Consul config and discovery")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.CONSUL)
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    consulApplicationService.init(project);
  }
}
