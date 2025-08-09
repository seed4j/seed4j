package com.seed4j.generator.server.springboot.springcloud.consul.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SERVICE_DISCOVERY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CONSUL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.springcloud.consul.application.ConsulApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConsulModuleConfiguration {

  @Bean
  SeedModuleResource consulModule(ConsulApplicationService consul) {
    return SeedModuleResource.builder()
      .slug(CONSUL)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Consul config and discovery")
      .organization(SeedModuleOrganization.builder().feature(SERVICE_DISCOVERY).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(consul::buildModule);
  }
}
