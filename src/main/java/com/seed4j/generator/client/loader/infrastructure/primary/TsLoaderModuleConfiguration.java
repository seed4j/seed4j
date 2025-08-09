package com.seed4j.generator.client.loader.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;

import com.seed4j.generator.client.loader.application.TsLoaderModuleApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.Seed4JModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TsLoaderModuleConfiguration {

  @Bean
  SeedModuleResource tsLoaderModule(TsLoaderModuleApplicationService tsLoader) {
    return SeedModuleResource.builder()
      .slug(Seed4JModuleSlug.TS_LOADER)
      .withoutProperties()
      .apiDoc("Frontend", "Helper class to represent loading states")
      .organization(SeedModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client")
      .factory(tsLoader::buildModule);
  }
}
