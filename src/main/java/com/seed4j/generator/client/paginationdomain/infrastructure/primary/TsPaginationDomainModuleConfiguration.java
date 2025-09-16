package com.seed4j.generator.client.paginationdomain.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;

import com.seed4j.generator.client.paginationdomain.application.TsPaginationDomainApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TsPaginationDomainModuleConfiguration {

  @Bean
  Seed4JModuleResource tsPaginationDomainModule(TsPaginationDomainApplicationService tsPaginationDomain) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.TS_PAGINATION_DOMAIN)
      .withoutProperties()
      .apiDoc("Pagination", "Add webapp domain for pagination")
      .organization(Seed4JModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "webapp", "frontend")
      .factory(tsPaginationDomain::buildModule);
  }
}
