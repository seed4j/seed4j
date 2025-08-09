package com.seed4j.generator.client.svelte.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SVELTE_CORE;

import com.seed4j.generator.client.svelte.core.application.SvelteApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SvelteModuleConfiguration {

  @Bean
  SeedModuleResource svelteModule(SvelteApplicationService svelte) {
    return SeedModuleResource.builder()
      .slug(SVELTE_CORE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addNodePackageManager().build())
      .apiDoc("Frontend - Svelte", "Add Svelte")
      .organization(SeedModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "svelte")
      .factory(svelte::buildModule);
  }
}
