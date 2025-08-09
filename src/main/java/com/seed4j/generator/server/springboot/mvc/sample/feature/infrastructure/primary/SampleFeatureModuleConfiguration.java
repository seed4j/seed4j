package com.seed4j.generator.server.springboot.mvc.sample.feature.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CUCUMBER_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.KIPE_AUTHORIZATION;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.KIPE_EXPRESSION;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SAMPLE_FEATURE;

import com.seed4j.generator.server.springboot.mvc.sample.feature.application.SampleFeatureApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleFeatureModuleConfiguration {

  @Bean
  SeedModuleResource sampleFeatureModule(SampleFeatureApplicationService sampleFeature) {
    return SeedModuleResource.builder()
      .slug(SAMPLE_FEATURE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Sample Feature", "Add sample context with some APIs")
      .organization(
        SeedModuleOrganization.builder()
          .addDependency(CUCUMBER_AUTHENTICATION)
          .addDependency(SPRINGDOC)
          .addDependency(JAVA_BASE)
          .addDependency(KIPE_EXPRESSION)
          .addDependency(KIPE_AUTHORIZATION)
          .build()
      )
      .tags("server")
      .factory(sampleFeature::buildModule);
  }
}
