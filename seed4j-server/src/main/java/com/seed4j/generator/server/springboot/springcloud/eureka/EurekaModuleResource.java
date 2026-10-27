package com.seed4j.generator.server.springboot.springcloud.eureka;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.EUREKA_CLIENT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_CLOUD;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class EurekaModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(EUREKA_CLIENT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Eureka Client")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(new EurekaModuleFactory(services)::buildModule);
  }
}
