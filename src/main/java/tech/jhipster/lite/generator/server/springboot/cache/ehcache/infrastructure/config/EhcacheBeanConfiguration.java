package tech.jhipster.lite.generator.server.springboot.cache.ehcache.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheDomainService;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheService;
import tech.jhipster.lite.generator.server.springboot.cache.jcache.domain.SpringBootJCacheService;
import tech.jhipster.lite.generator.server.springboot.properties.domain.SpringBootPropertiesService;

@Configuration
public class EhcacheBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootJCacheService springBootJCacheService;
  private final SpringBootPropertiesService springBootPropertiesService;

  public EhcacheBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootJCacheService springBootJCacheService,
    SpringBootPropertiesService springBootPropertiesService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootJCacheService = springBootJCacheService;
    this.springBootPropertiesService = springBootPropertiesService;
  }

  @Bean
  public EhcacheService ehcacheService() {
    return new EhcacheDomainService(buildToolService, projectRepository, springBootJCacheService, springBootPropertiesService);
  }
}
