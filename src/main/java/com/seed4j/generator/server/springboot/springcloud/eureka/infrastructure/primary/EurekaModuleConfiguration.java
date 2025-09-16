package com.seed4j.generator.server.springboot.springcloud.eureka.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.EUREKA_CLIENT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.eureka.application.EurekaApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EurekaModuleConfiguration {

  @Bean
  Seed4JModuleResource eurekaModule(EurekaApplicationService eureka) {
    return Seed4JModuleResource.builder()
      .slug(EUREKA_CLIENT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Eureka Client")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(eureka::buildModule);
  }
}
