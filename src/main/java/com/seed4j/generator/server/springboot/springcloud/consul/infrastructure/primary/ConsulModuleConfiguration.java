package com.seed4j.generator.server.springboot.springcloud.consul.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SERVICE_DISCOVERY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CONSUL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.springcloud.consul.application.ConsulApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConsulModuleConfiguration {

  @Bean
  Seed4JModuleResource consulModule(ConsulApplicationService consul) {
    return Seed4JModuleResource.builder()
      .slug(CONSUL)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Consul config and discovery")
      .organization(Seed4JModuleOrganization.builder().feature(SERVICE_DISCOVERY).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(consul::buildModule);
  }
}
