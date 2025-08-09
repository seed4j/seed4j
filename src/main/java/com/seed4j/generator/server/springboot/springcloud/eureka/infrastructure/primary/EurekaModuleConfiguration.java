package com.seed4j.generator.server.springboot.springcloud.eureka.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.EUREKA_CLIENT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.eureka.application.EurekaApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EurekaModuleConfiguration {

  @Bean
  SeedModuleResource eurekaModule(EurekaApplicationService eureka) {
    return SeedModuleResource.builder()
      .slug(EUREKA_CLIENT)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Eureka Client")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(eureka::buildModule);
  }
}
