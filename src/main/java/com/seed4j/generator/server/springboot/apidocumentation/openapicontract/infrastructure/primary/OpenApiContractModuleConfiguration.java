package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.OPENAPI_BACKWARDS_COMPATIBILITY_CHECK;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.OPENAPI_CONTRACT;

import com.seed4j.generator.server.springboot.apidocumentation.openapicontract.application.OpenApiContractApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiContractModuleConfiguration {

  @Bean
  JHipsterModuleResource openApiContractModule(OpenApiContractApplicationService openApiContract) {
    return JHipsterModuleResource.builder()
      .slug(OPENAPI_CONTRACT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - OpenAPI", "Generates OpenAPI contract at build time using openapi-maven-plugin")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_MVC_SERVER).addDependency(MAVEN_JAVA).build())
      .tags("server", "spring", "spring-boot", "documentation", "swagger", "openapi")
      .factory(openApiContract::buildModule);
  }

  @Bean
  JHipsterModuleResource openApiBackwardsCompatibilityCheckModule(OpenApiContractApplicationService openApiContract) {
    return JHipsterModuleResource.builder()
      .slug(OPENAPI_BACKWARDS_COMPATIBILITY_CHECK)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.EMPTY)
      .apiDoc("Spring Boot - OpenAPI", "Check backwards incompatible changes to OpenAPI contract during build")
      .organization(JHipsterModuleOrganization.builder().addDependency(OPENAPI_CONTRACT).build())
      .tags("server", "spring", "spring-boot", "documentation", "swagger", "openapi")
      .factory(openApiContract::buildBackwardsCompatibilityCheckModule);
  }
}
