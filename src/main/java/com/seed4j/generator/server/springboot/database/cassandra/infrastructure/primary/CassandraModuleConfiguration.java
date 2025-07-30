package com.seed4j.generator.server.springboot.database.cassandra.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CASSANDRA;

import com.seed4j.generator.server.springboot.database.cassandra.application.CassandraApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CassandraModuleConfiguration {

  @Bean
  JHipsterModuleResource cassandraModule(CassandraApplicationService cassandra) {
    return JHipsterModuleResource.builder()
      .slug(CASSANDRA)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Cassandra drivers and dependencies")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(cassandra::buildModule);
  }
}
