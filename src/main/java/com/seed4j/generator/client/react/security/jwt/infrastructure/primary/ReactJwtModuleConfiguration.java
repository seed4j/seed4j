package com.seed4j.generator.client.react.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_JWT;

import com.seed4j.generator.client.react.security.jwt.application.ReactJwtApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactJwtModuleConfiguration {

  @Bean
  Seed4JModuleResource reactJwtModule(ReactJwtApplicationService reactJwt) {
    return Seed4JModuleResource.builder()
      .slug(REACT_JWT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - React", "Add JWT Login React")
      .organization(Seed4JModuleOrganization.builder().addDependency(REACT_CORE).build())
      .tags("client", "react", "jwt")
      .factory(reactJwt::buildModule);
  }
}
