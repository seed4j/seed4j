package com.seed4j.generator.client.vue.i18n.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.VUE_I18N;

import com.seed4j.generator.client.vue.i18n.application.VueI18nApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueI18nModuleConfiguration {

  @Bean
  SeedModuleResource vueI18nModule(VueI18nApplicationService vueI18n) {
    return SeedModuleResource.builder()
      .slug(VUE_I18N)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - Vue", "Add vue internationalization")
      .organization(SeedModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "i18n")
      .factory(vueI18n::buildModule);
  }
}
