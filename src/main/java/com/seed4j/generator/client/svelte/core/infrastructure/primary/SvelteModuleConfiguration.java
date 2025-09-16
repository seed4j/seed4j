package com.seed4j.generator.client.svelte.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SVELTE_CORE;

import com.seed4j.generator.client.svelte.core.application.SvelteApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SvelteModuleConfiguration {

  @Bean
  Seed4JModuleResource svelteModule(SvelteApplicationService svelte) {
    return Seed4JModuleResource.builder()
      .slug(SVELTE_CORE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addNodePackageManager().build())
      .apiDoc("Frontend - Svelte", "Add Svelte")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "svelte")
      .factory(svelte::buildModule);
  }
}
