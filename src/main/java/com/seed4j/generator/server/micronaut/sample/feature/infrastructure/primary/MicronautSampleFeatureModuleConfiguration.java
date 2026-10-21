package com.seed4j.generator.server.micronaut.sample.feature.infrastructure.primary;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_JWT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_OPENAPI;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_SAMPLE_FEATURE;

import com.seed4j.generator.server.micronaut.sample.feature.application.MicronautSampleFeatureApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautSampleFeatureModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautSampleFeatureModule(MicronautSampleFeatureApplicationService sampleFeature) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_SAMPLE_FEATURE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Micronaut Sample Feature", "Add sample context with some APIs for Micronaut")
      .organization(
        Seed4JModuleOrganization.builder().addDependency(MICRONAUT).addDependency(MICRONAUT_OPENAPI).addDependency(MICRONAUT_JWT).build()
      )
      .tags("server", "micronaut", "sample")
      .factory(sampleFeature::buildModule);
  }
}
