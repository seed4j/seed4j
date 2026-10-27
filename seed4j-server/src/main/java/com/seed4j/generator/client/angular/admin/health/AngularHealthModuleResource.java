package com.seed4j.generator.client.angular.admin.health;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_HEALTH;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class AngularHealthModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_HEALTH)
      .withoutProperties()
      .apiDoc("Frontend - Angular", "Angular Health")
      .organization(Seed4JModuleOrganization.builder().addDependency(ANGULAR_CORE).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("client", "angular", "health")
      .factory(new AngularHealthModuleFactory()::buildModule);
  }
}
