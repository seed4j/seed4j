package com.seed4j.generator.server.springboot.core;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SpringBootCoreModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot", "Init Spring Boot project with dependencies, App, and properties")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(JAVA_BASE).build())
      .tags("server", "spring", "spring-boot")
      .factory(new SpringBootCoreModuleFactory()::buildModule);
  }
}
