package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.AUTHENTICATION_SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRINGDOC_JWT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_JWT;

import com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.application.SpringdocJwtApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource springdocJwtModule(SpringdocJwtApplicationService springdocJwt) {
    return JHipsterModuleResource.builder()
      .slug(SPRINGDOC_JWT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - API Documentation", "Add JWT authentication for springdoc")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(AUTHENTICATION_SPRINGDOC)
          .addDependency(SPRINGDOC)
          .addDependency(SPRING_BOOT_JWT)
          .build()
      )
      .tags("server", "swagger", "springdoc")
      .factory(springdocJwt::buildModule);
  }
}
