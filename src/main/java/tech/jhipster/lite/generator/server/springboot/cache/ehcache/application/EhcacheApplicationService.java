package tech.jhipster.lite.generator.server.springboot.cache.ehcache.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheService;

@Service
public class EhcacheApplicationService {

  private final EhcacheService ehcacheService;

  public EhcacheApplicationService(EhcacheService ehcacheService) {
    this.ehcacheService = ehcacheService;
  }

  public void init(Project project) {
    ehcacheService.init(project);
  }

  public void addDependencies(Project project) {
    ehcacheService.addDependencies(project);
  }

  public void addJavaFiles(Project project) {
    ehcacheService.addJavaFiles(project);
  }

  public void addProperties(Project project) {
    ehcacheService.addProperties(project);
  }
}
