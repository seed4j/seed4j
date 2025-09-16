package com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MONGOCK;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_MONGODB_PERSISTENCE;

import com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.application.SampleMongoDBPersistenceApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleMongoDBPersistenceModuleConfiguration {

  @Bean
  Seed4JModuleResource sampleMongoDBModule(SampleMongoDBPersistenceApplicationService sampleMongoDBPersistence) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_MONGODB_PERSISTENCE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add MongoDB persistence for sample feature")
      .organization(
        Seed4JModuleOrganization.builder().feature(SAMPLE_PERSISTENCE).addDependency(SAMPLE_FEATURE).addDependency(MONGOCK).build()
      )
      .tags("server")
      .factory(sampleMongoDBPersistence::buildModule);
  }
}
