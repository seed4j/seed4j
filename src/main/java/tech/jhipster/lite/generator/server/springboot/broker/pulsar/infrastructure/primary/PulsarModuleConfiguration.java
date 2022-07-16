package tech.jhipster.lite.generator.server.springboot.broker.pulsar.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.broker.pulsar.application.PulsarApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class PulsarModuleConfiguration {

  @Bean
  JHipsterModuleResource pulsarModule(PulsarApplicationService pulsar) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/brokers/pulsar")
      .slug("springboot-pulsar")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Broker", "Add Pulsar dependencies, with testcontainers"))
      .tags("server", "spring", "spring-boot", "broker")
      .factory(pulsar::buildModule);
  }
}
