package com.seed4j.generator.server.springboot.springcloud.gateway;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GATEWAY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_EMPTY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_CLOUD;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class GatewayModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(GATEWAY)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Gateway")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_WEBFLUX_EMPTY).addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(new GatewayModuleFactory()::buildModule);
  }
}
