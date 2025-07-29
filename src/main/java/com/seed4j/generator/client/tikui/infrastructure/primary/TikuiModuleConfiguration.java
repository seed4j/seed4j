package com.seed4j.generator.client.tikui.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.TIKUI;

import com.seed4j.generator.client.tikui.application.TikuiApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TikuiModuleConfiguration {

  @Bean
  JHipsterModuleResource tikuiModule(TikuiApplicationService tikui) {
    return JHipsterModuleResource.builder()
      .slug(TIKUI)
      .withoutProperties()
      .apiDoc("Frontend", "Add Tikui, a pattern library to build your styles")
      .organization(JHipsterModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "frontend", "tikui")
      .factory(tikui::buildModule);
  }
}
