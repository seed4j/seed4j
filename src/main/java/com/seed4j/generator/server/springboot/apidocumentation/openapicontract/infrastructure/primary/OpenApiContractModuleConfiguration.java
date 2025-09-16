package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.OPENAPI_BACKWARDS_COMPATIBILITY_CHECK;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.OPENAPI_CONTRACT;

import com.seed4j.generator.server.springboot.apidocumentation.openapicontract.application.OpenApiContractApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiContractModuleConfiguration {

  @Bean
  Seed4JModuleResource openApiContractModule(OpenApiContractApplicationService openApiContract) {
    return Seed4JModuleResource.builder()
      .slug(OPENAPI_CONTRACT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - OpenAPI", "Generates OpenAPI contract at build time using openapi-maven-plugin")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_MVC_SERVER).addDependency(MAVEN_JAVA).build())
      .tags("server", "spring", "spring-boot", "documentation", "swagger", "openapi")
      .factory(openApiContract::buildModule);
  }

  @Bean
  Seed4JModuleResource openApiBackwardsCompatibilityCheckModule(OpenApiContractApplicationService openApiContract) {
    return Seed4JModuleResource.builder()
      .slug(OPENAPI_BACKWARDS_COMPATIBILITY_CHECK)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.EMPTY)
      .apiDoc("Spring Boot - OpenAPI", "Check backwards incompatible changes to OpenAPI contract during build")
      .organization(Seed4JModuleOrganization.builder().addDependency(OPENAPI_CONTRACT).build())
      .tags("server", "spring", "spring-boot", "documentation", "swagger", "openapi")
      .factory(openApiContract::buildBackwardsCompatibilityCheckModule);
  }
}
