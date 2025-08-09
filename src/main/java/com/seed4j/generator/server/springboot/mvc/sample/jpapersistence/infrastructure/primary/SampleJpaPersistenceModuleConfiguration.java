package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_JPA_RESET;

import com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.application.SampleJpaPersistenceApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleJpaPersistenceModuleConfiguration {

  @Bean
  SeedModuleResource sampleJpaPersistenceModule(SampleJpaPersistenceApplicationService sampleJpaPersistence) {
    return SeedModuleResource.builder()
      .slug(SAMPLE_JPA_PERSISTENCE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add JPA persistence for sample feature")
      .organization(
        SeedModuleOrganization.builder()
          .feature(SAMPLE_PERSISTENCE)
          .addDependency(SAMPLE_SCHEMA)
          .addDependency(SPRING_BOOT_CUCUMBER_JPA_RESET)
          .build()
      )
      .tags("server")
      .factory(sampleJpaPersistence::buildModule);
  }
}
