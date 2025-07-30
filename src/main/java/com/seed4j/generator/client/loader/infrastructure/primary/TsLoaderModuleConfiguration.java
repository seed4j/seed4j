package com.seed4j.generator.client.loader.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;

import com.seed4j.generator.client.loader.application.TsLoaderModuleApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TsLoaderModuleConfiguration {

  @Bean
  JHipsterModuleResource tsLoaderModule(TsLoaderModuleApplicationService tsLoader) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.TS_LOADER)
      .withoutProperties()
      .apiDoc("Frontend", "Helper class to represent loading states")
      .organization(JHipsterModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client")
      .factory(tsLoader::buildModule);
  }
}
