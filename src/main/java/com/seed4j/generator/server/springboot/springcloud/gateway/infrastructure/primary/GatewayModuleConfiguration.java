package com.seed4j.generator.server.springboot.springcloud.gateway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GATEWAY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_EMPTY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.gateway.application.GatewayApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GatewayModuleConfiguration {

  @Bean
  Seed4JModuleResource gatewayModule(GatewayApplicationService gateway) {
    return Seed4JModuleResource.builder()
      .slug(GATEWAY)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Gateway")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_WEBFLUX_EMPTY).addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(gateway::buildModule);
  }
}
