package com.seed4j.generator.client.react.i18n.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REACT_I18N;

import com.seed4j.generator.client.react.i18n.application.ReactI18nApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactI18nModuleConfiguration {

  @Bean
  JHipsterModuleResource reactI18nModule(ReactI18nApplicationService reactI18n) {
    return JHipsterModuleResource.builder()
      .slug(REACT_I18N)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - React", "Add react internationalization")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(REACT_CORE).build())
      .tags("client", "react", "i18n")
      .factory(reactI18n::buildModule);
  }
}
