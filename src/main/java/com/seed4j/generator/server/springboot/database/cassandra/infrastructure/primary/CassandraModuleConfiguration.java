package com.seed4j.generator.server.springboot.database.cassandra.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CASSANDRA;

import com.seed4j.generator.server.springboot.database.cassandra.application.CassandraApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CassandraModuleConfiguration {

  @Bean
  SeedModuleResource cassandraModule(CassandraApplicationService cassandra) {
    return SeedModuleResource.builder()
      .slug(CASSANDRA)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Cassandra drivers and dependencies")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(cassandra::buildModule);
  }
}
