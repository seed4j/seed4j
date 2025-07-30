package com.seed4j.generator.server.springboot.mvc.sample.flyway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FLYWAY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FLYWAY_POSTGRESQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JPA_POSTGRESQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_NOT_POSTGRESQL_FLYWAY_CHANGELOG;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_POSTGRESQL_FLYWAY_CHANGELOG;

import com.seed4j.generator.server.springboot.mvc.sample.flyway.application.SampleFlywayApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleFlywayModuleConfiguration {

  @Bean
  JHipsterModuleResource sampleFlywayPostgreSQLModule(SampleFlywayApplicationService sampleFlyway) {
    return JHipsterModuleResource.builder()
      .slug(SAMPLE_POSTGRESQL_FLYWAY_CHANGELOG)
      .withoutProperties()
      .apiDoc("Sample Feature", "Add PostgreSQL flyway changelog for sample feature")
      .organization(
        JHipsterModuleOrganization.builder()
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
  JHipsterModuleResource sampleFlywayNotPostgreSQLModule(SampleFlywayApplicationService sampleFlyway) {
    return JHipsterModuleResource.builder()
      .slug(SAMPLE_NOT_POSTGRESQL_FLYWAY_CHANGELOG)
      .withoutProperties()
      .apiDoc("Sample Feature", "Add not PostgreSQL flyway changelog for sample feature")
      .organization(JHipsterModuleOrganization.builder().feature(SAMPLE_SCHEMA).addDependency(FLYWAY).addDependency(SAMPLE_FEATURE).build())
      .tags("server")
      .factory(sampleFlyway::buildNotPostgreSQLModule);
  }
}
