package tech.jhipster.lite.generator.server.springboot.cucumber.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cucumber.application.CucumberApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CucumberModuleConfiguration {

  private static final String SPRING_BOOT_COMPONENT_TESTS_API_GROUP = "Spring Boot - Component Tests";
  private static final String SERVER_TAG = "server";
  private static final String SPRING_TAG = "spring";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String TEST_TAG = "test";

  @Bean
  JHipsterModuleResource cucumberMvcInitializationModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_CUCUMBER_MVC)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Spring MVC to project")
      .organization(JHipsterModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_MVC_SERVER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource cucumberWebfluxInitializationModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_CUCUMBER_WEBFLUX)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Webflux to project")
      .organization(JHipsterModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_BOOT_WEBFLUX_NETTY).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource cucumberJpaResetModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_CUCUMBER_JPA_RESET)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add jpa reset for cucumber")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_CUCUMBER).addDependency(JPA_PERSISTENCE).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildJpaResetModule);
  }
}
