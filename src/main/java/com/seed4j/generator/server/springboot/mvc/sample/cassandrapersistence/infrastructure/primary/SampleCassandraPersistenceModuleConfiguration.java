package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CASSANDRA_MIGRATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_CASSANDRA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_FEATURE;

import com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.application.SampleCassandraPersistenceApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleCassandraPersistenceModuleConfiguration {

  @Bean
  JHipsterModuleResource sampleCassandraPersistenceModule(SampleCassandraPersistenceApplicationService sampleCassandraPersistence) {
    return JHipsterModuleResource.builder()
      .slug(SAMPLE_CASSANDRA_PERSISTENCE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add Cassandra persistence for sample feature")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(SAMPLE_PERSISTENCE)
          .addDependency(SAMPLE_FEATURE)
          .addDependency(CASSANDRA_MIGRATION)
          .build()
      )
      .tags("server")
      .factory(sampleCassandraPersistence::buildModule);
  }
}
