package com.seed4j.generator.client.loader.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;

import com.seed4j.generator.client.loader.application.TsLoaderModuleApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TsLoaderModuleConfiguration {

  @Bean
  SeedModuleResource tsLoaderModule(TsLoaderModuleApplicationService tsLoader) {
    return SeedModuleResource.builder()
      .slug(JHLiteModuleSlug.TS_LOADER)
      .withoutProperties()
      .apiDoc("Frontend", "Helper class to represent loading states")
      .organization(SeedModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client")
      .factory(tsLoader::buildModule);
  }
}
