package com.seed4j.generator.client.vue.core;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class VueModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(VUE_CORE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().addNodePackageManager().build())
      .apiDoc("Frontend - Vue", "Add Vue+Vite")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", "init", "vue")
      .factory(new VueModuleFactory(services)::buildModule);
  }
}
