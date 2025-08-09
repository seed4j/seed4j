package com.seed4j.generator.client.vue.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.*;

import com.seed4j.generator.client.vue.core.application.VueApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VueModuleConfiguration {

  @Bean
  SeedModuleResource vueCoreModule(VueApplicationService vue) {
    return SeedModuleResource.builder()
      .slug(VUE_CORE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().addNodePackageManager().build())
      .apiDoc("Frontend - Vue", "Add Vue+Vite")
      .organization(SeedModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", "init", "vue")
      .factory(vue::buildModule);
  }
}
