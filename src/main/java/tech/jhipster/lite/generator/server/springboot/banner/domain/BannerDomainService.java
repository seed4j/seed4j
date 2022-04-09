package tech.jhipster.lite.generator.server.springboot.banner.domain;

import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;

import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class BannerDomainService implements BannerService {

  public static final String SOURCE = "server/springboot/banner";

  private final ProjectRepository projectRepository;

  public BannerDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addBannerJHipsterV7(Project project) {
    addBanner(project, "banner-jhipster-v7.txt");
  }

  @Override
  public void addBannerIppon(Project project) {
    addBanner(project, "banner-ippon.txt");
  }

  @Override
  public void addBannerJHipsterV7React(Project project) {
    addBanner(project, "banner-jhipster-v7-react.txt");
  }

  @Override
  public void addBannerJHipsterV7Vue(Project project) {
    addBanner(project, "banner-jhipster-v7-vue.txt");
  }

  @Override
  public void addBannerJHipsterV2(Project project) {
    addBanner(project, "banner-jhipster-v2.txt");
  }

  @Override
  public void addBannerJHipsterV3(Project project) {
    addBanner(project, "banner-jhipster-v3.txt");
  }

  private void addBanner(Project project, String bannerFilename) {
    projectRepository.add(project, SOURCE, bannerFilename, MAIN_RESOURCES, "banner.txt");
  }
}
