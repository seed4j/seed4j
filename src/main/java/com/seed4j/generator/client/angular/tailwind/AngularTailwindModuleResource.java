package com.seed4j.generator.client.angular.tailwind;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_TAILWIND;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class AngularTailwindModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_TAILWIND)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - Angular", "Add Tailwind CSS to an Angular project")
      .organization(Seed4JModuleOrganization.builder().addDependency(ANGULAR_CORE).build())
      .tags("client", "angular", "tailwind")
      .factory(new AngularTailwindModuleFactory()::buildModule);
  }
}
