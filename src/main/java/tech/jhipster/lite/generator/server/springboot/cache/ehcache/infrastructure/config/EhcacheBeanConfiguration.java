package tech.jhipster.lite.generator.server.springboot.cache.ehcache.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.cache.common.domain.SpringBootCacheService;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheDomainService;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class EhcacheBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCacheService springBootCacheService;
  private final SpringBootCommonService springBootCommonService;

  public EhcacheBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCacheService springBootCacheService,
    SpringBootCommonService springBootCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCacheService = springBootCacheService;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public EhcacheService ehcacheService() {
    return new EhcacheDomainService(buildToolService, projectRepository, springBootCacheService, springBootCommonService);
  }
}
