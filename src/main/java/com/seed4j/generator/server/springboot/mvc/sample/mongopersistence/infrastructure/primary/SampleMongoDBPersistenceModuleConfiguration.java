package com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MONGOCK;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_MONGODB_PERSISTENCE;

import com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.application.SampleMongoDBPersistenceApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleMongoDBPersistenceModuleConfiguration {

  @Bean
  SeedModuleResource sampleMongoDBModule(SampleMongoDBPersistenceApplicationService sampleMongoDBPersistence) {
    return SeedModuleResource.builder()
      .slug(SAMPLE_MONGODB_PERSISTENCE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add MongoDB persistence for sample feature")
      .organization(
        SeedModuleOrganization.builder().feature(SAMPLE_PERSISTENCE).addDependency(SAMPLE_FEATURE).addDependency(MONGOCK).build()
      )
      .tags("server")
      .factory(sampleMongoDBPersistence::buildModule);
  }
}
