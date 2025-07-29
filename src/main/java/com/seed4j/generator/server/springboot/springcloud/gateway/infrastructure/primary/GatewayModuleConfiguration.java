package com.seed4j.generator.server.springboot.springcloud.gateway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GATEWAY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_WEBFLUX_EMPTY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.gateway.application.GatewayApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GatewayModuleConfiguration {

  @Bean
  JHipsterModuleResource gatewayModule(GatewayApplicationService gateway) {
    return JHipsterModuleResource.builder()
      .slug(GATEWAY)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Gateway")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_WEBFLUX_EMPTY).addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(gateway::buildModule);
  }
}
