package com.seed4j.generator.client.react.i18n.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_I18N;

import com.seed4j.generator.client.react.i18n.application.ReactI18nApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactI18nModuleConfiguration {

  @Bean
  Seed4JModuleResource reactI18nModule(ReactI18nApplicationService reactI18n) {
    return Seed4JModuleResource.builder()
      .slug(REACT_I18N)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - React", "Add react internationalization")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(REACT_CORE).build())
      .tags("client", "react", "i18n")
      .factory(reactI18n::buildModule);
  }
}
