package com.seed4j.generator.client.vue.pinia.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_PINIA;

import com.seed4j.generator.client.vue.pinia.application.VuePiniaApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VuePiniaModuleConfiguration {

  @Bean
  Seed4JModuleResource vuePiniaModule(VuePiniaApplicationService vuePinia) {
    return Seed4JModuleResource.builder()
      .slug(VUE_PINIA)
      .withoutProperties()
      .apiDoc("Frontend - Vue", "Add pinia for state management")
      .organization(Seed4JModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(vuePinia::buildModule);
  }
}
