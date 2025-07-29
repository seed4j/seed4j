package com.seed4j.generator.client.vue.router.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.VUE_ROUTER;

import com.seed4j.generator.client.vue.router.application.VueRouterApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueRouterModuleConfiguration {

  @Bean
  JHipsterModuleResource vueRouterModule(VueRouterApplicationService vueRouter) {
    return JHipsterModuleResource.builder()
      .slug(VUE_ROUTER)
      .withoutProperties()
      .apiDoc("Frontend - Vue", "Add Vue Router")
      .organization(JHipsterModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(vueRouter::buildModule);
  }
}
