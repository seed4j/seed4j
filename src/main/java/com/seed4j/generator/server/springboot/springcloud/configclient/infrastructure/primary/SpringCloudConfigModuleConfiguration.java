package com.seed4j.generator.server.springboot.springcloud.configclient.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SERVICE_DISCOVERY;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_ACTUATOR;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.configclient.application.SpringCloudConfigClientApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringCloudConfigModuleConfiguration {

  @Bean
  SeedModuleResource springCloudConfigModule(SpringCloudConfigClientApplicationService cloudConfigs) {
    return SeedModuleResource.builder()
      .slug(SPRING_CLOUD)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Config Client")
      .organization(SeedModuleOrganization.builder().feature(SERVICE_DISCOVERY).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(cloudConfigs::buildModule);
  }
}
