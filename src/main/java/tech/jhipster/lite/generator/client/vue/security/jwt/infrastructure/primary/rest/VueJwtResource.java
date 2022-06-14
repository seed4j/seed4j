package tech.jhipster.lite.generator.client.vue.security.jwt.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.vue.security.jwt.application.VueJwtApplicationService;
import tech.jhipster.lite.generator.project.domain.GeneratorAction;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/vue")
@Tag(name = "Vue")
class VueJwtResource {

  private final VueJwtApplicationService vueJwtApplicationService;

  public VueJwtResource(VueJwtApplicationService vueJwtApplicationService) {
    this.vueJwtApplicationService = vueJwtApplicationService;
  }

  @Operation(summary = "Add JWT to vue ap", description = "Add Jwt")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding JWT on Vue")
  @PostMapping("/jwt")
  @GeneratorStep(id = GeneratorAction.VUE_JWT)
  public void addVueJwt(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    vueJwtApplicationService.addJWT(project);
  }
}
