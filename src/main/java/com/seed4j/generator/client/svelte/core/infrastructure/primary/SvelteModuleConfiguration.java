package com.seed4j.generator.client.svelte.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SVELTE_CORE;

import com.seed4j.generator.client.svelte.core.application.SvelteApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SvelteModuleConfiguration {

  @Bean
  JHipsterModuleResource svelteModule(SvelteApplicationService svelte) {
    return JHipsterModuleResource.builder()
      .slug(SVELTE_CORE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addNodePackageManager().build())
      .apiDoc("Frontend - Svelte", "Add Svelte")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "svelte")
      .factory(svelte::buildModule);
  }
}
