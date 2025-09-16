package com.seed4j.generator.client.tikui.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TIKUI;

import com.seed4j.generator.client.tikui.application.TikuiApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TikuiModuleConfiguration {

  @Bean
  Seed4JModuleResource tikuiModule(TikuiApplicationService tikui) {
    return Seed4JModuleResource.builder()
      .slug(TIKUI)
      .withoutProperties()
      .apiDoc("Frontend", "Add Tikui, a pattern library to build your styles")
      .organization(Seed4JModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "frontend", "tikui")
      .factory(tikui::buildModule);
  }
}
