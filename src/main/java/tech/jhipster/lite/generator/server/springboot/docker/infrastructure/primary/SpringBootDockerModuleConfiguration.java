package tech.jhipster.lite.generator.server.springboot.docker.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.docker.application.SpringBootDockerApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class SpringBootDockerModuleConfiguration {

  @Bean
  JHipsterModuleResource jibModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/containers/docker/jib")
      .slug("jib")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addServerPort().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Tools", "Add Docker image building with Jib"))
      .tags("server", "spring", "spring-boot", "jib")
      .factory(springBootDocker::buildJibModule);
  }

  @Bean
  JHipsterModuleResource dockerFileModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/containers/docker/dockerfile")
      .slug("dockerfile")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Tools", "Add Dockerfile"))
      .tags("server", "spring", "spring-boot", "docker")
      .factory(springBootDocker::buildDockerFileModule);
  }
}
