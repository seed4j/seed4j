package com.seed4j.generator.server.springboot.springcloud.gateway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GATEWAY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_WEBFLUX_EMPTY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.gateway.application.GatewayApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GatewayModuleConfiguration {

  @Bean
  SeedModuleResource gatewayModule(GatewayApplicationService gateway) {
    return SeedModuleResource.builder()
      .slug(GATEWAY)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Gateway")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_WEBFLUX_EMPTY).addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(gateway::buildModule);
  }
}
