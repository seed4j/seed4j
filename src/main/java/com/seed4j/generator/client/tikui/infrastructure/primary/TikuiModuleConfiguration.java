package com.seed4j.generator.client.tikui.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.TIKUI;

import com.seed4j.generator.client.tikui.application.TikuiApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TikuiModuleConfiguration {

  @Bean
  SeedModuleResource tikuiModule(TikuiApplicationService tikui) {
    return SeedModuleResource.builder()
      .slug(TIKUI)
      .withoutProperties()
      .apiDoc("Frontend", "Add Tikui, a pattern library to build your styles")
      .organization(SeedModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "frontend", "tikui")
      .factory(tikui::buildModule);
  }
}
