package com.seed4j.generator.server.springboot.mvc.sample.feature.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CUCUMBER_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.KIPE_AUTHORIZATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.KIPE_EXPRESSION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_FEATURE;

import com.seed4j.generator.server.springboot.mvc.sample.feature.application.SampleFeatureApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleFeatureModuleConfiguration {

  @Bean
  JHipsterModuleResource sampleFeatureModule(SampleFeatureApplicationService sampleFeature) {
    return JHipsterModuleResource.builder()
      .slug(SAMPLE_FEATURE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Sample Feature", "Add sample context with some APIs")
      .organization(
        JHipsterModuleOrganization.builder()
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
