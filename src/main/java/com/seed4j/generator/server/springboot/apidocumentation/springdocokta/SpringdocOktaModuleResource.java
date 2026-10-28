package com.seed4j.generator.server.springboot.apidocumentation.springdocokta;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.OAUTH_PROVIDER_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_OAUTH_2_OKTA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2_OKTA;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SpringdocOktaModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_OAUTH_2_OKTA)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - API Documentation", "Add Okta authentication for springdoc")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(OAUTH_PROVIDER_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_OAUTH_2_OKTA)
          .build()
      )
      .tags("server", "swagger", "springdoc", "okta")
      .factory(new SpringdocOktaModuleFactory()::buildModule);
  }
}
