package com.seed4j.generator.client.vue.security.jwt.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.*;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.*;

import com.seed4j.generator.client.vue.security.jwt.application.VueJwtApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource vueJwtModule(VueJwtApplicationService vueJwt) {
    return JHipsterModuleResource.builder()
      .slug(VUE_JWT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Vue", "Add JWT authentication to Vue")
      .organization(JHipsterModuleOrganization.builder().feature(VUE_AUTHENTICATION).addDependency(VUE_CORE).build())
      .tags("client", "vue", "auth", "jwt")
      .factory(vueJwt::buildModule);
  }
}
