package com.seed4j.generator.client.angular.security.jwt;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.ANGULAR_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_JWT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class AngularJwtModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_JWT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Frontend - Angular", "Add Angular with authentication JWT")
      .organization(Seed4JModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(new AngularJwtModuleFactory()::buildModule);
  }
}
