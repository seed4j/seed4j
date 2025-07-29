package com.seed4j.generator.client.vue.i18n.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_I18N;

import com.seed4j.generator.client.vue.i18n.application.VueI18nApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueI18nModuleConfiguration {

  @Bean
  JHipsterModuleResource vueI18nModule(VueI18nApplicationService vueI18n) {
    return JHipsterModuleResource.builder()
      .slug(VUE_I18N)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - Vue", "Add vue internationalization")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "i18n")
      .factory(vueI18n::buildModule);
  }
}
