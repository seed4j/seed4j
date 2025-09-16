package com.seed4j.generator.client.vue.router.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_ROUTER;

import com.seed4j.generator.client.vue.router.application.VueRouterApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueRouterModuleConfiguration {

  @Bean
  Seed4JModuleResource vueRouterModule(VueRouterApplicationService vueRouter) {
    return Seed4JModuleResource.builder()
      .slug(VUE_ROUTER)
      .withoutProperties()
      .apiDoc("Frontend - Vue", "Add Vue Router")
      .organization(Seed4JModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(vueRouter::buildModule);
  }
}
