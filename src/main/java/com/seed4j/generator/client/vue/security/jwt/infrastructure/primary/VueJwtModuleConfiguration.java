package com.seed4j.generator.client.vue.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.*;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.seed4j.generator.client.vue.security.jwt.application.VueJwtApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueJwtModuleConfiguration {

  @Bean
  Seed4JModuleResource vueJwtModule(VueJwtApplicationService vueJwt) {
    return Seed4JModuleResource.builder()
      .slug(VUE_JWT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Vue", "Add JWT authentication to Vue")
      .organization(Seed4JModuleOrganization.builder().feature(VUE_AUTHENTICATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "auth", "jwt")
      .factory(vueJwt::buildModule);
  }
}
