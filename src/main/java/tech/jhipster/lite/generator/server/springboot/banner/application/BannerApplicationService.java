package tech.jhipster.lite.generator.server.springboot.banner.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.banner.domain.BannerService;

@Component
public record BannerApplicationService(BannerService bannerService) {
  public void addBannerJHipsterV7(Project project) {
    bannerService.addBannerJHipsterV7(project);
  }

  public void addBannerIppon(Project project) {
    bannerService.addBannerIppon(project);
  }

  public void addBannerJHipsterV7React(Project project) {
    bannerService.addBannerJHipsterV7React(project);
  }

  public void addBannerJHipsterV7Vue(Project project) {
    bannerService.addBannerJHipsterV7Vue(project);
  }

  public void addBannerJHipsterV2(Project project) {
    bannerService.addBannerJHipsterV2(project);
  }

  public void addBannerJHipsterV3(Project project) {
    bannerService.addBannerJHipsterV3(project);
  }
}
