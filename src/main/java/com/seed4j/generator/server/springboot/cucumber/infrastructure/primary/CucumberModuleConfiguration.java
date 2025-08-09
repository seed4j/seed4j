package com.seed4j.generator.server.springboot.cucumber.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_BOOT_CUCUMBER;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_JPA_RESET;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_MVC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CUCUMBER_WEBFLUX;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.seed4j.generator.server.springboot.cucumber.application.CucumberApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
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
  SeedModuleResource cucumberMvcInitializationModule(CucumberApplicationService cucumber) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_MVC)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Spring MVC to project")
      .organization(SeedModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_MVC_SERVER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  SeedModuleResource cucumberWebfluxInitializationModule(CucumberApplicationService cucumber) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_WEBFLUX)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Webflux to project")
      .organization(SeedModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_BOOT_WEBFLUX_NETTY).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  SeedModuleResource cucumberJpaResetModule(CucumberApplicationService cucumber) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_JPA_RESET)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add jpa reset for cucumber")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_CUCUMBER).addDependency(JPA_PERSISTENCE).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildJpaResetModule);
  }
}
