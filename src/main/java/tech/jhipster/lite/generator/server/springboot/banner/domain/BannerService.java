package tech.jhipster.lite.generator.server.springboot.banner.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface BannerService {
  void addBannerJHipsterV7(Project project);
  void addBannerIppon(Project project);
  void addBannerJHipsterV7React(Project project);
  void addBannerJHipsterV7Vue(Project project);
  void addBannerJHipsterV2(Project project);
  void addBannerJHipsterV3(Project project);
}
