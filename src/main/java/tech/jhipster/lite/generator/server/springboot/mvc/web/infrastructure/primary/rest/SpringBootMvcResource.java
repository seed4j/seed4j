package tech.jhipster.lite.generator.server.springboot.mvc.web.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot")
@Tag(name = "Spring Boot - MVC")
class SpringBootMvcResource {

  private final SpringBootMvcApplicationService springBootMvcApplicationService;

  public SpringBootMvcResource(SpringBootMvcApplicationService springBootMvcApplicationService) {
    this.springBootMvcApplicationService = springBootMvcApplicationService;
  }

  @Operation(summary = "Add Spring Boot MVC with Tomcat")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Boot MVC with Tomcat")
  @PostMapping("/web-servers/tomcat")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_TOMCAT)
  public void addSpringBootMvc(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootMvcApplicationService.addSpringBootMvc(project);
  }

  @Operation(summary = "Add Spring Boot MVC with Undertow")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Boot MVC with Undertow")
  @PostMapping("/web-servers/undertow")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_UNDERTOW)
  public void addSpringBootUndertow(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootMvcApplicationService.addSpringBootUndertow(project);
  }

  @Operation(summary = "Add Spring Boot Actuator")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Boot Actuator")
  @PostMapping("/technical-tools/actuator")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_ACTUATOR)
  public void addSpringBootActuator(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootMvcApplicationService.addSpringBootActuator(project);
  }
}
