package com.seed4j.generator.server.springboot.database.cassandra.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CASSANDRA;

import com.seed4j.generator.server.springboot.database.cassandra.application.CassandraApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CassandraModuleConfiguration {

  @Bean
  Seed4JModuleResource cassandraModule(CassandraApplicationService cassandra) {
    return Seed4JModuleResource.builder()
      .slug(CASSANDRA)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Cassandra drivers and dependencies")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(cassandra::buildModule);
  }
}
