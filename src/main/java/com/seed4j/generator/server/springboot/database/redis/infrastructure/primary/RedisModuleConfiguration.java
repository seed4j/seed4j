package com.seed4j.generator.server.springboot.database.redis.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.REDIS;

import com.seed4j.generator.server.springboot.database.redis.application.RedisApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedisModuleConfiguration {

  @Bean
  SeedModuleResource redisModule(RedisApplicationService redis) {
    return SeedModuleResource.builder()
      .slug(REDIS)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Redis drivers and dependencies, with testcontainers")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(redis::buildModule);
  }
}
