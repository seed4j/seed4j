package tech.jhipster.lite.generator.client.vue.core.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.vue.core.application.VueApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class VueModuleConfiguration {

  @Bean
  JHipsterModuleResource vueModule(VueApplicationService vue) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/clients/vue")
      .slug("vue")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Vue", "Add Vue+Vite"))
      .tags("client", "init", "vue")
      .factory(vue::buildVueModule);
  }

  @Bean
  JHipsterModuleResource vuePiniaModule(VueApplicationService vue) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/clients/vue/stores/pinia")
      .slug("vue-pinia")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Vue", "Add pinia for state management"))
      .tags("client", "vue", "store")
      .factory(vue::buildPiniaModule);
  }
}
