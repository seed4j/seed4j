package com.seed4j.generator.server.springboot.springcloud.configclient.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SERVICE_DISCOVERY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_CLOUD;

import com.seed4j.generator.server.springboot.springcloud.configclient.application.SpringCloudConfigClientApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringCloudConfigModuleConfiguration {

  @Bean
  JHipsterModuleResource springCloudConfigModule(SpringCloudConfigClientApplicationService cloudConfigs) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_CLOUD)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Config Client")
      .organization(JHipsterModuleOrganization.builder().feature(SERVICE_DISCOVERY).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(cloudConfigs::buildModule);
  }
}
