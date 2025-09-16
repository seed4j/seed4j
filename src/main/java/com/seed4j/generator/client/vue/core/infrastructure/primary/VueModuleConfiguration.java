package com.seed4j.generator.client.vue.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.seed4j.generator.client.vue.core.application.VueApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueModuleConfiguration {

  @Bean
  Seed4JModuleResource vueCoreModule(VueApplicationService vue) {
    return Seed4JModuleResource.builder()
      .slug(VUE_CORE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().addNodePackageManager().build())
      .apiDoc("Frontend - Vue", "Add Vue+Vite")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", "init", "vue")
      .factory(vue::buildModule);
  }
}
