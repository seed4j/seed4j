package com.seed4j.generator.server.springboot.springcloud.configclient.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SERVICE_DISCOVERY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.configclient.application.SpringCloudConfigClientApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringCloudConfigModuleConfiguration {

  @Bean
  Seed4JModuleResource springCloudConfigModule(SpringCloudConfigClientApplicationService cloudConfigs) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_CLOUD)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Config Client")
      .organization(Seed4JModuleOrganization.builder().feature(SERVICE_DISCOVERY).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(cloudConfigs::buildModule);
  }
}
