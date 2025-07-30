package com.seed4j.generator.server.springboot.cucumber.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_BOOT_CUCUMBER;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_JPA_RESET;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_MVC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_WEBFLUX;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.seed4j.generator.server.springboot.cucumber.application.CucumberApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CucumberModuleConfiguration {

  private static final String SPRING_BOOT_COMPONENT_TESTS_API_GROUP = "Spring Boot - Component Tests";
  private static final String SERVER_TAG = "server";
  private static final String SPRING_TAG = "spring";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String TEST_TAG = "test";

  @Bean
  JHipsterModuleResource cucumberMvcInitializationModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_MVC)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Spring MVC to project")
      .organization(JHipsterModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_MVC_SERVER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource cucumberWebfluxInitializationModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_WEBFLUX)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Webflux to project")
      .organization(JHipsterModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_BOOT_WEBFLUX_NETTY).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource cucumberJpaResetModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_JPA_RESET)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add jpa reset for cucumber")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_CUCUMBER).addDependency(JPA_PERSISTENCE).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildJpaResetModule);
  }
}
