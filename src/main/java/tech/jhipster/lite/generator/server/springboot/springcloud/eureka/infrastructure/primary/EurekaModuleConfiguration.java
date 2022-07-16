package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class EurekaModuleConfiguration {

  @Bean
  JHipsterModuleResource eurekaModule(EurekaApplicationService eureka) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/distributed-systems/spring-cloud/eureka-client")
      .slug("eureka-client")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Eureka Client"))
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(eureka::buildModule);
  }
}
