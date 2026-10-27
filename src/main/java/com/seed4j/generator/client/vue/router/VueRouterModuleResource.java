package com.seed4j.generator.client.vue.router;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.VUE_ROUTER;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class VueRouterModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(VUE_ROUTER)
      .withoutProperties()
      .apiDoc("Frontend - Vue", "Add Vue Router")
      .organization(Seed4JModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(new VueRouterModuleFactory()::buildModule);
  }
}
