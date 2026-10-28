package com.seed4j.generator.server.springboot.springcloud.configclient;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SERVICE_DISCOVERY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_CLOUD;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SpringCloudConfigModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_CLOUD)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Config Client")
      .organization(Seed4JModuleOrganization.builder().feature(SERVICE_DISCOVERY).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(new SpringCloudConfigModuleFactory(services)::buildModule);
  }
}
