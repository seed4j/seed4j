package com.seed4j.generator.server.springboot.database.redis.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REDIS;

import com.seed4j.generator.server.springboot.database.redis.application.RedisApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedisModuleConfiguration {

  @Bean
  JHipsterModuleResource redisModule(RedisApplicationService redis) {
    return JHipsterModuleResource.builder()
      .slug(REDIS)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Redis drivers and dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(redis::buildModule);
  }
}
