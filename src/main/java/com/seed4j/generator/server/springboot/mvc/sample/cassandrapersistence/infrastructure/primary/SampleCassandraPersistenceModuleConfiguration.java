package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CASSANDRA_MIGRATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_CASSANDRA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;

import com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.application.SampleCassandraPersistenceApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleCassandraPersistenceModuleConfiguration {

  @Bean
  Seed4JModuleResource sampleCassandraPersistenceModule(SampleCassandraPersistenceApplicationService sampleCassandraPersistence) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_CASSANDRA_PERSISTENCE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add Cassandra persistence for sample feature")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(SAMPLE_PERSISTENCE)
          .addDependency(SAMPLE_FEATURE)
          .addDependency(CASSANDRA_MIGRATION)
          .build()
      )
      .tags("server")
      .factory(sampleCassandraPersistence::buildModule);
  }
}
