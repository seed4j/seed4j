package com.seed4j.generator.client.vue.router.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_ROUTER;

import com.seed4j.generator.client.vue.router.application.VueRouterApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueRouterModuleConfiguration {

  @Bean
  SeedModuleResource vueRouterModule(VueRouterApplicationService vueRouter) {
    return SeedModuleResource.builder()
      .slug(VUE_ROUTER)
      .withoutProperties()
      .apiDoc("Frontend - Vue", "Add Vue Router")
      .organization(SeedModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(vueRouter::buildModule);
  }
}
