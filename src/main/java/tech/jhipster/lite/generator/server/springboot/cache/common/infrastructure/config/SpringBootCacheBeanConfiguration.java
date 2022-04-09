package tech.jhipster.lite.generator.server.springboot.cache.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheDomainService;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class SpringBootCacheBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;

  public SpringBootCacheBeanConfiguration(BuildToolService buildToolService, ProjectRepository projectRepository) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
  }

  @Bean
  public SpringBootCacheService springBootCacheService() {
    return new SpringBootCacheDomainService(buildToolService, projectRepository);
  }
}
