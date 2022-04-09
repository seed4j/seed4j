package tech.jhipster.lite.generator.client.vue.core.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.vue.core.application.VueApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/vue")
@Tag(name = "Vue")
class VueResource {

  private final VueApplicationService vueApplicationService;

  public VueResource(VueApplicationService vueApplicationService) {
    this.vueApplicationService = vueApplicationService;
  }

  @Operation(summary = "Add Vue+Vite", description = "Add Vue+Vite")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Vue+Vite")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.VUE)
  public void addVue(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    vueApplicationService.addVue(project);
  }

  @Operation(summary = "Add Vue+Vite with minimal CSS", description = "Add Vue+Vite with Minimal CSS")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Vue+Vite with minimal CSS")
  @PostMapping("/styles")
  @GeneratorStep(id = GeneratorAction.VUE_STYLED)
  public void addStyledVue(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    vueApplicationService.addStyledVue(project);
  }

  @Operation(summary = "Add Pinia", description = "Add pinia for state management")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Pinia")
  @PostMapping("/stores/pinia")
  @GeneratorStep(id = GeneratorAction.VUE_PINIA)
  public void addPinia(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    vueApplicationService.addPinia(project);
  }
}
