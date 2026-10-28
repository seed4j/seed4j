package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.AUTHENTICATION_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_JWT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_JWT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SpringdocJwtModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_JWT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - API Documentation", "Add JWT authentication for springdoc")
      .organization(
        Seed4JModuleOrganization.builder().feature(AUTHENTICATION_SPRINGDOC).addDependency(SPRINGDOC).addDependency(SPRING_BOOT_JWT).build()
      )
      .tags("server", "swagger", "springdoc")
      .factory(new SpringdocJwtModuleFactory()::buildModule);
  }
}
