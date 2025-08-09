package com.seed4j.generator.client.vue.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.*;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.*;

import com.seed4j.generator.client.vue.security.jwt.application.VueJwtApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueJwtModuleConfiguration {

  @Bean
  SeedModuleResource vueJwtModule(VueJwtApplicationService vueJwt) {
    return SeedModuleResource.builder()
      .slug(VUE_JWT)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Vue", "Add JWT authentication to Vue")
      .organization(SeedModuleOrganization.builder().feature(VUE_AUTHENTICATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "auth", "jwt")
      .factory(vueJwt::buildModule);
  }
}
