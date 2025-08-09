package com.seed4j.generator.client.react.i18n.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.REACT_I18N;

import com.seed4j.generator.client.react.i18n.application.ReactI18nApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactI18nModuleConfiguration {

  @Bean
  SeedModuleResource reactI18nModule(ReactI18nApplicationService reactI18n) {
    return SeedModuleResource.builder()
      .slug(REACT_I18N)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - React", "Add react internationalization")
      .organization(SeedModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(REACT_CORE).build())
      .tags("client", "react", "i18n")
      .factory(reactI18n::buildModule);
  }
}
