package tech.jhipster.light.generator.server.springboot.cache.caffeine.application;

import org.springframework.stereotype.Component;
import tech.jhipster.light.generator.project.domain.Project;
import tech.jhipster.light.generator.server.springboot.cache.caffeine.domain.CaffeineService;

@Component
public class CaffeineApplicationService {

  private final CaffeineService caffeineService;

  public CaffeineApplicationService(CaffeineService caffeineService) {
    this.caffeineService = caffeineService;
  }

  public void initCache(Project project) {
    caffeineService.initCache(project);
  }

}
