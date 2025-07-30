package com.seed4j.generator.server.springboot.apidocumentation.springdoccore.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRINGDOC_MVC_OPENAPI;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRINGDOC_WEBFLUX_OPENAPI;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.seed4j.generator.server.springboot.apidocumentation.springdoccore.application.SpringdocApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
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
  JHipsterModuleResource springdocMvcModule(SpringdocApplicationService springdoc) {
    return JHipsterModuleResource.builder()
      .slug(SPRINGDOC_MVC_OPENAPI)
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(API_GROUP, "Add springdoc-openapi for spring MVC")
      .organization(JHipsterModuleOrganization.builder().feature(SPRINGDOC).addDependency(SPRING_MVC_SERVER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCUMENTATION_TAG, SWAGGER_TAG)
      .factory(springdoc::buildSpringdocMvcModule);
  }

  @Bean
  JHipsterModuleResource springdocWebfluxModule(SpringdocApplicationService springdoc) {
    return JHipsterModuleResource.builder()
      .slug(SPRINGDOC_WEBFLUX_OPENAPI)
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(API_GROUP, "Add springdoc-openapi for webflux")
      .organization(JHipsterModuleOrganization.builder().feature(SPRINGDOC).addDependency(SPRING_BOOT_WEBFLUX_NETTY).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCUMENTATION_TAG, SWAGGER_TAG)
      .factory(springdoc::buildSpringdocWebfluxModule);
  }

  private JHipsterModulePropertiesDefinition buildPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder()
      .addBasePackage()
      .addProjectBaseName()
      .addIndentation()
      .addSpringConfigurationFormat()
      .build();
  }
}
