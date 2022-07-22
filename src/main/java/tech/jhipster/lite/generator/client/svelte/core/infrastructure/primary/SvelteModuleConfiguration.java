package tech.jhipster.lite.generator.client.svelte.core.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.svelte.core.application.SvelteApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
public class SvelteModuleConfiguration {

  @Bean
  JHipsterModuleResource svelteModule(SvelteApplicationService svelteApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/javascript-languages/svelte")
      .slug("svelte")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Svelte", "Add Svelte"))
      .organization(JHipsterModuleOrganization.builder().feature("client-core").addFeatureDependency("startup").build())
      .tags("client", "svelte")
      .factory(svelteApplicationService::buildModule);
  }
}
