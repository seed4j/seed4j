package com.seed4j.generator.server.springboot.technicaltools.prometheus.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_PROMETHEUS;

import com.seed4j.generator.server.springboot.technicaltools.prometheus.application.SpringBootPrometheusApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootPrometheusModuleConfiguration {

  @Bean
  Seed4JModuleResource springBootPrometheusModule(SpringBootPrometheusApplicationService springBootPrometheus) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_PROMETHEUS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Expose Prometheus metrics at /management/prometheus")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "spring-boot", "prometheus", "monitoring")
      .factory(springBootPrometheus::buildModule);
  }
}
