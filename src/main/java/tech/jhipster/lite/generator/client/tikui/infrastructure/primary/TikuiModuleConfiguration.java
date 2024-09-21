package tech.jhipster.lite.generator.client.tikui.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.tikui.application.TikuiApplicationService;
import tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug;
import tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class TikuiModuleConfiguration {

  @Bean
  JHipsterModuleResource tikuiModule(TikuiApplicationService tikui) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.TIKUI)
      .withoutProperties()
      .apiDoc("Frontend", "Add Tikui, a pattern library to build your styles")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.CLIENT_CORE).build())
      .tags("client", "frontend", "tikui")
      .factory(tikui::buildModule);
  }
}
