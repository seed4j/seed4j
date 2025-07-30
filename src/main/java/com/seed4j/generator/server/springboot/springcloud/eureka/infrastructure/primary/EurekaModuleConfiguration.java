package com.seed4j.generator.server.springboot.springcloud.eureka.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.EUREKA_CLIENT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.eureka.application.EurekaApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EurekaModuleConfiguration {

  @Bean
  JHipsterModuleResource eurekaModule(EurekaApplicationService eureka) {
    return JHipsterModuleResource.builder()
      .slug(EUREKA_CLIENT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Eureka Client")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(eureka::buildModule);
  }
}
