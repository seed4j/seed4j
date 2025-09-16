package com.seed4j.generator.client.vue.i18n.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_I18N;

import com.seed4j.generator.client.vue.i18n.application.VueI18nApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueI18nModuleConfiguration {

  @Bean
  Seed4JModuleResource vueI18nModule(VueI18nApplicationService vueI18n) {
    return Seed4JModuleResource.builder()
      .slug(VUE_I18N)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - Vue", "Add vue internationalization")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "i18n")
      .factory(vueI18n::buildModule);
  }
}
