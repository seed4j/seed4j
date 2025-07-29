package com.seed4j.generator.client.vue.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.*;

import com.seed4j.generator.client.vue.core.application.VueApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueModuleConfiguration {

  @Bean
  JHipsterModuleResource vueCoreModule(VueApplicationService vue) {
    return JHipsterModuleResource.builder()
      .slug(VUE_CORE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().addNodePackageManager().build())
      .apiDoc("Frontend - Vue", "Add Vue+Vite")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", "init", "vue")
      .factory(vue::buildModule);
  }
}
