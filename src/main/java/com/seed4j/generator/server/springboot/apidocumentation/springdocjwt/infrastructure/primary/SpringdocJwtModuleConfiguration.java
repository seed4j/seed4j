package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.AUTHENTICATION_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRINGDOC_JWT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_JWT;

import com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.application.SpringdocJwtApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocJwtModuleConfiguration {

  @Bean
  SeedModuleResource springdocJwtModule(SpringdocJwtApplicationService springdocJwt) {
    return SeedModuleResource.builder()
      .slug(SPRINGDOC_JWT)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - API Documentation", "Add JWT authentication for springdoc")
      .organization(
        SeedModuleOrganization.builder().feature(AUTHENTICATION_SPRINGDOC).addDependency(SPRINGDOC).addDependency(SPRING_BOOT_JWT).build()
      )
      .tags("server", "swagger", "springdoc")
      .factory(springdocJwt::buildModule);
  }
}
