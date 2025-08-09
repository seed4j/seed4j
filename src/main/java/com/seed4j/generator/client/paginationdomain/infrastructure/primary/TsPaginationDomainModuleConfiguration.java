package com.seed4j.generator.client.paginationdomain.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;

import com.seed4j.generator.client.paginationdomain.application.TsPaginationDomainApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.Seed4JModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TsPaginationDomainModuleConfiguration {

  @Bean
  SeedModuleResource tsPaginationDomainModule(TsPaginationDomainApplicationService tsPaginationDomain) {
    return SeedModuleResource.builder()
      .slug(Seed4JModuleSlug.TS_PAGINATION_DOMAIN)
      .withoutProperties()
      .apiDoc("Pagination", "Add webapp domain for pagination")
      .organization(SeedModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "webapp", "frontend")
      .factory(tsPaginationDomain::buildModule);
  }
}
