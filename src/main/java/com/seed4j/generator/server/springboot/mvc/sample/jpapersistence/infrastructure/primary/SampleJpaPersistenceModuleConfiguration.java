package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CUCUMBER_JPA_RESET;

import com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.application.SampleJpaPersistenceApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleJpaPersistenceModuleConfiguration {

  @Bean
  Seed4JModuleResource sampleJpaPersistenceModule(SampleJpaPersistenceApplicationService sampleJpaPersistence) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_JPA_PERSISTENCE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add JPA persistence for sample feature")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(SAMPLE_PERSISTENCE)
          .addDependency(SAMPLE_SCHEMA)
          .addDependency(SPRING_BOOT_CUCUMBER_JPA_RESET)
          .build()
      )
      .tags("server")
      .factory(sampleJpaPersistence::buildModule);
  }
}
