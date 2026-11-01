package com.seed4j.generator.client.svelte.core;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SVELTE_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SvelteModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SVELTE_CORE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().addNodePackageManager().build())
      .apiDoc("Frontend - Svelte", "Add Svelte+SvelteKit")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", "init", "svelte")
      .factory(new SvelteModuleFactory(services)::buildModule);
  }
}
