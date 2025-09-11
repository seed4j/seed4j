package com.seed4j.generator.client.angular.i18n.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_I18N;

import com.seed4j.generator.client.angular.i18n.application.AngularI18nApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularI18nModuleConfiguration {

  @Bean
  Seed4JModuleResource angularI18nModule(AngularI18nApplicationService angularI18n) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_I18N)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - Angular", "Add Angular internationalization")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular", "i18n")
      .factory(angularI18n::buildModule);
  }
}
