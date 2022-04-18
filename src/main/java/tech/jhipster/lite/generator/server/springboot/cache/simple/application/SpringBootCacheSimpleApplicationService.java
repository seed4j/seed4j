package tech.jhipster.lite.generator.server.springboot.cache.simple.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.cache.simple.domain.SpringBootCacheSimpleService;

@Service
public record SpringBootCacheSimpleApplicationService(SpringBootCacheSimpleService springBootCacheSimpleService) {
  public void init(Project project) {
    springBootCacheSimpleService.init(project);
  }
}
