package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.AUTHENTICATION_SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRINGDOC_JWT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_JWT;

import com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.application.SpringdocJwtApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocJwtModuleConfiguration {

  @Bean
  Seed4JModuleResource springdocJwtModule(SpringdocJwtApplicationService springdocJwt) {
    return Seed4JModuleResource.builder()
      .slug(SPRINGDOC_JWT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - API Documentation", "Add JWT authentication for springdoc")
      .organization(
        Seed4JModuleOrganization.builder().feature(AUTHENTICATION_SPRINGDOC).addDependency(SPRINGDOC).addDependency(SPRING_BOOT_JWT).build()
      )
      .tags("server", "swagger", "springdoc")
      .factory(springdocJwt::buildModule);
  }
}
