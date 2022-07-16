package tech.jhipster.lite.generator.server.springboot.springcloud.consul.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.application.ConsulApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class ConsulModuleConfiguration {

  @Bean
  JHipsterModuleResource consulModule(ConsulApplicationService consulApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/distributed-systems/spring-cloud/consul")
      .slug("consul")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Consul config and discovery"))
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(consulApplicationService::buildModule);
  }
}
