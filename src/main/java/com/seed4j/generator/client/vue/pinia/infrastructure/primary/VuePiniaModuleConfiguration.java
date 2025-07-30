package com.seed4j.generator.client.vue.pinia.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_PINIA;

import com.seed4j.generator.client.vue.pinia.application.VuePiniaApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VuePiniaModuleConfiguration {

  @Bean
  JHipsterModuleResource vuePiniaModule(VuePiniaApplicationService vuePinia) {
    return JHipsterModuleResource.builder()
      .slug(VUE_PINIA)
      .withoutProperties()
      .apiDoc("Frontend - Vue", "Add pinia for state management")
      .organization(JHipsterModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(vuePinia::buildModule);
  }
}
