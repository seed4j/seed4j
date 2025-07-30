package com.seed4j.generator.client.react.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REACT_JWT;

import com.seed4j.generator.client.react.security.jwt.application.ReactJwtApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource reactJwtModule(ReactJwtApplicationService reactJwt) {
    return JHipsterModuleResource.builder()
      .slug(REACT_JWT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - React", "Add JWT Login React")
      .organization(JHipsterModuleOrganization.builder().addDependency(REACT_CORE).build())
      .tags("client", "react", "jwt")
      .factory(reactJwt::buildModule);
  }
}
