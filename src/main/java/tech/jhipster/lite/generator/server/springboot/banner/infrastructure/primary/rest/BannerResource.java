package tech.jhipster.lite.generator.server.springboot.banner.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.server.springboot.banner.application.BannerApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/banners")
@Tag(name = "Spring Boot")
class BannerResource {

  private final BannerApplicationService bannerApplicationService;

  public BannerResource(BannerApplicationService bannerApplicationService) {
    this.bannerApplicationService = bannerApplicationService;
  }

  @Operation(summary = "Add banner JHipster v7 for Angular")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding banner")
  @PostMapping("/jhipster-v7")
  @GeneratorStep(id = GeneratorAction.BANNER_JHIPSTER_V7)
  public void addBannerJHipsterV7(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV7(project);
  }

  @Operation(summary = "Add banner for Ippon applications")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding banner")
  @PostMapping("/ippon")
  @GeneratorStep(id = GeneratorAction.BANNER_IPPON)
  public void addBannerIppon(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerIppon(project);
  }

  @Operation(summary = "Add banner JHipster v7 for React")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding banner")
  @PostMapping("/jhipster-v7-react")
  @GeneratorStep(id = GeneratorAction.BANNER_JHIPSTER_V7_REACT)
  public void addBannerJHipsterV7React(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV7React(project);
  }

  @Operation(summary = "Add banner JHipster v7 for Vue")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding banner")
  @PostMapping("/jhipster-v7-vue")
  @GeneratorStep(id = GeneratorAction.BANNER_JHIPSTER_V7_VUE)
  public void addBannerJHipsterV7Vue(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV7Vue(project);
  }

  @Operation(summary = "Add banner JHipster v2")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding banner")
  @PostMapping("/jhipster-v2")
  @GeneratorStep(id = GeneratorAction.BANNER_JHIPSTER_V2)
  public void addBannerJHipsterV2(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV2(project);
  }

  @Operation(summary = "Add banner JHipster v3")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding banner")
  @PostMapping("/jhipster-v3")
  @GeneratorStep(id = GeneratorAction.BANNER_JHIPSTER_V3)
  public void addBannerJHipsterV3(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    bannerApplicationService.addBannerJHipsterV3(project);
  }
}
