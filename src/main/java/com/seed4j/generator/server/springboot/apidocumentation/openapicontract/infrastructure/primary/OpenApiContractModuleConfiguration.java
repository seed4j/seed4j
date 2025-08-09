package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.OPENAPI_BACKWARDS_COMPATIBILITY_CHECK;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.OPENAPI_CONTRACT;

import com.seed4j.generator.server.springboot.apidocumentation.openapicontract.application.OpenApiContractApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiContractModuleConfiguration {

  @Bean
  SeedModuleResource openApiContractModule(OpenApiContractApplicationService openApiContract) {
    return SeedModuleResource.builder()
      .slug(OPENAPI_CONTRACT)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - OpenAPI", "Generates OpenAPI contract at build time using openapi-maven-plugin")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_MVC_SERVER).addDependency(MAVEN_JAVA).build())
      .tags("server", "spring", "spring-boot", "documentation", "swagger", "openapi")
      .factory(openApiContract::buildModule);
  }

  @Bean
  SeedModuleResource openApiBackwardsCompatibilityCheckModule(OpenApiContractApplicationService openApiContract) {
    return SeedModuleResource.builder()
      .slug(OPENAPI_BACKWARDS_COMPATIBILITY_CHECK)
      .propertiesDefinition(SeedModulePropertiesDefinition.EMPTY)
      .apiDoc("Spring Boot - OpenAPI", "Check backwards incompatible changes to OpenAPI contract during build")
      .organization(SeedModuleOrganization.builder().addDependency(OPENAPI_CONTRACT).build())
      .tags("server", "spring", "spring-boot", "documentation", "swagger", "openapi")
      .factory(openApiContract::buildBackwardsCompatibilityCheckModule);
  }
}
