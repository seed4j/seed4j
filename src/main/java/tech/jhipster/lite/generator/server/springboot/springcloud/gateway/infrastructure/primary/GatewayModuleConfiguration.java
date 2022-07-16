package tech.jhipster.lite.generator.server.springboot.springcloud.gateway.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.gateway.application.GatewayApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class GatewayModuleConfiguration {

  @Bean
  JHipsterModuleResource gatewayModule(GatewayApplicationService gateway) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/distributed-systems/spring-cloud/gateway")
      .slug("gateway")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Gateway"))
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(gateway::buildModule);
  }
}
