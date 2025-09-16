package com.seed4j.generator.server.springboot.cucumber.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_BOOT_CUCUMBER;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CUCUMBER_JPA_RESET;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CUCUMBER_MVC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CUCUMBER_WEBFLUX;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.seed4j.generator.server.springboot.cucumber.application.CucumberApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
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
  Seed4JModuleResource cucumberMvcInitializationModule(CucumberApplicationService cucumber) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_MVC)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Spring MVC to project")
      .organization(Seed4JModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_MVC_SERVER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  Seed4JModuleResource cucumberWebfluxInitializationModule(CucumberApplicationService cucumber) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_WEBFLUX)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add Cucumber integration for Webflux to project")
      .organization(Seed4JModuleOrganization.builder().feature(SPRING_BOOT_CUCUMBER).addDependency(SPRING_BOOT_WEBFLUX_NETTY).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  Seed4JModuleResource cucumberJpaResetModule(CucumberApplicationService cucumber) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_CUCUMBER_JPA_RESET)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc(SPRING_BOOT_COMPONENT_TESTS_API_GROUP, "Add jpa reset for cucumber")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_CUCUMBER).addDependency(JPA_PERSISTENCE).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, TEST_TAG)
      .factory(cucumber::buildJpaResetModule);
  }
}
