package com.seed4j.generator.server.springboot.mvc.sample.flyway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_NOT_POSTGRESQL_FLYWAY_CHANGELOG;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_POSTGRESQL_FLYWAY_CHANGELOG;

import com.seed4j.generator.server.springboot.mvc.sample.flyway.application.SampleFlywayApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleFlywayModuleConfiguration {

  @Bean
  Seed4JModuleResource sampleFlywayPostgreSQLModule(SampleFlywayApplicationService sampleFlyway) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_POSTGRESQL_FLYWAY_CHANGELOG)
      .withoutProperties()
      .apiDoc("Sample Feature", "Add PostgreSQL flyway changelog for sample feature")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(SAMPLE_SCHEMA)
          .addDependency(FLYWAY_POSTGRESQL)
          .addDependency(SAMPLE_FEATURE)
          .addDependency(JPA_POSTGRESQL)
          .build()
      )
      .tags("server")
      .factory(sampleFlyway::buildPostgreSQLModule);
  }

  @Bean
  Seed4JModuleResource sampleFlywayNotPostgreSQLModule(SampleFlywayApplicationService sampleFlyway) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_NOT_POSTGRESQL_FLYWAY_CHANGELOG)
      .withoutProperties()
      .apiDoc("Sample Feature", "Add not PostgreSQL flyway changelog for sample feature")
      .organization(Seed4JModuleOrganization.builder().feature(SAMPLE_SCHEMA).addDependency(FLYWAY).addDependency(SAMPLE_FEATURE).build())
      .tags("server")
      .factory(sampleFlyway::buildNotPostgreSQLModule);
  }
}
