package com.seed4j.generator.client.react.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REACT_JWT;

import com.seed4j.generator.client.react.security.jwt.application.ReactJwtApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactJwtModuleConfiguration {

  @Bean
  SeedModuleResource reactJwtModule(ReactJwtApplicationService reactJwt) {
    return SeedModuleResource.builder()
      .slug(REACT_JWT)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - React", "Add JWT Login React")
      .organization(SeedModuleOrganization.builder().addDependency(REACT_CORE).build())
      .tags("client", "react", "jwt")
      .factory(reactJwt::buildModule);
  }
}
