package com.seed4j.generator.client.vue.pinia.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.VUE_PINIA;

import com.seed4j.generator.client.vue.pinia.application.VuePiniaApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VuePiniaModuleConfiguration {

  @Bean
  SeedModuleResource vuePiniaModule(VuePiniaApplicationService vuePinia) {
    return SeedModuleResource.builder()
      .slug(VUE_PINIA)
      .withoutProperties()
      .apiDoc("Frontend - Vue", "Add pinia for state management")
      .organization(SeedModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(vuePinia::buildModule);
  }
}
