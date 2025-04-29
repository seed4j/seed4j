package tech.jhipster.lite.generator.client.vue.i18n.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.VUE_CORE;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.VUE_I18N;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.vue.i18n.application.VueI18nApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

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
