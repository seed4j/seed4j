package com.seed4j.generator.server.springboot.apidocumentation.springdoccore.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_MVC_OPENAPI;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_WEBFLUX_OPENAPI;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.seed4j.generator.server.springboot.apidocumentation.springdoccore.application.SpringdocApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocModuleConfiguration {

  private static final String API_GROUP = "Spring Boot - API Documentation";
  private static final String SERVER_TAG = "server";
  private static final String SWAGGER_TAG = "server";
  private static final String SPRING_TAG = "spring";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String DOCUMENTATION_TAG = "documentation";

  @Bean
  Seed4JModuleResource springdocMvcModule(SpringdocApplicationService springdoc) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_MVC_OPENAPI)
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(API_GROUP, "Add springdoc-openapi for spring MVC")
      .organization(Seed4JModuleOrganization.builder().feature(SPRINGDOC).addDependency(SPRING_MVC_SERVER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCUMENTATION_TAG, SWAGGER_TAG)
      .factory(springdoc::buildSpringdocMvcModule);
  }

  @Bean
  Seed4JModuleResource springdocWebfluxModule(SpringdocApplicationService springdoc) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_WEBFLUX_OPENAPI)
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(API_GROUP, "Add springdoc-openapi for webflux")
      .organization(Seed4JModuleOrganization.builder().feature(SPRINGDOC).addDependency(SPRING_BOOT_WEBFLUX_NETTY).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCUMENTATION_TAG, SWAGGER_TAG)
      .factory(springdoc::buildSpringdocWebfluxModule);
  }

  private Seed4JModulePropertiesDefinition buildPropertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder()
      .addBasePackage()
      .addProjectBaseName()
      .addIndentation()
      .addSpringConfigurationFormat()
      .build();
  }
}
