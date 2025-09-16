package com.seed4j.generator.client.loader.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;

import com.seed4j.generator.client.loader.application.TsLoaderModuleApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TsLoaderModuleConfiguration {

  @Bean
  Seed4JModuleResource tsLoaderModule(TsLoaderModuleApplicationService tsLoader) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.TS_LOADER)
      .withoutProperties()
      .apiDoc("Frontend", "Helper class to represent loading states")
      .organization(Seed4JModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client")
      .factory(tsLoader::buildModule);
  }
}
