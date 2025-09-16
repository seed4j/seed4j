package com.seed4j.generator.server.springboot.database.redis.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REDIS;

import com.seed4j.generator.server.springboot.database.redis.application.RedisApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedisModuleConfiguration {

  @Bean
  Seed4JModuleResource redisModule(RedisApplicationService redis) {
    return Seed4JModuleResource.builder()
      .slug(REDIS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Redis drivers and dependencies, with testcontainers")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(redis::buildModule);
  }
}
