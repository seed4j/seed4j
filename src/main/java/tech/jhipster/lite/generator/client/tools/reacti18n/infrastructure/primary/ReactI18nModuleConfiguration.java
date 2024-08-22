package tech.jhipster.lite.generator.client.tools.reacti18n.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.REACT_CORE;
import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.REACT_I18N;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.tools.reacti18n.application.ReactI18nApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
public class ReactI18nModuleConfiguration {

  @Bean
  JHipsterModuleResource i18nModule(ReactI18nApplicationService i18n) {
    return JHipsterModuleResource.builder()
      .slug(REACT_I18N)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc("react i18n", "Add react internationalization")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(REACT_CORE).build())
      .tags("client", "react", "i18n")
      .factory(i18n::buildModule);
  }
}
