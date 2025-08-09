package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CASSANDRA_MIGRATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_CASSANDRA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_FEATURE;

import com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.application.SampleCassandraPersistenceApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleCassandraPersistenceModuleConfiguration {

  @Bean
  SeedModuleResource sampleCassandraPersistenceModule(SampleCassandraPersistenceApplicationService sampleCassandraPersistence) {
    return SeedModuleResource.builder()
      .slug(SAMPLE_CASSANDRA_PERSISTENCE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add Cassandra persistence for sample feature")
      .organization(
        SeedModuleOrganization.builder()
          .feature(SAMPLE_PERSISTENCE)
          .addDependency(SAMPLE_FEATURE)
          .addDependency(CASSANDRA_MIGRATION)
          .build()
      )
      .tags("server")
      .factory(sampleCassandraPersistence::buildModule);
  }
}
