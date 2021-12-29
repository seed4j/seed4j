package tech.jhipster.lite.generator.server.springboot.cache.jcache.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheService;
import tech.jhipster.lite.generator.server.springboot.cache.jcache.domain.SpringBootJCacheDomainService;
import tech.jhipster.lite.generator.server.springboot.cache.jcache.domain.SpringBootJCacheService;

@Configuration
public class SpringBootJCacheBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCacheService springBootCacheService;

  public SpringBootJCacheBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCacheService springBootCacheService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCacheService = springBootCacheService;
  }

  @Bean
  public SpringBootJCacheService springBootJCacheService() {
    return new SpringBootJCacheDomainService(buildToolService, springBootCacheService);
  }
}
