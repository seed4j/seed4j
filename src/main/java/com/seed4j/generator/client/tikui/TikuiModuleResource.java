package com.seed4j.generator.client.tikui;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TIKUI;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class TikuiModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(TIKUI)
      .withoutProperties()
      .apiDoc("Frontend", "Add Tikui, a pattern library to build your styles")
      .organization(Seed4JModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "frontend", "tikui")
      .factory(new TikuiModuleFactory(services)::buildModule);
  }
}
