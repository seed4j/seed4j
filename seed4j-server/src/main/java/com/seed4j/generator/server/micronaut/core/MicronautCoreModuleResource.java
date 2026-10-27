package com.seed4j.generator.server.micronaut.core;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class MicronautCoreModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut", "Init Micronaut project with dependencies, App, and properties")
      .organization(Seed4JModuleOrganization.builder().addDependency(MAVEN_JAVA).addDependency(JAVA_BASE).build())
      .tags("server", "micronaut")
      .factory(new MicronautCoreModuleFactory()::buildModule);
  }
}
