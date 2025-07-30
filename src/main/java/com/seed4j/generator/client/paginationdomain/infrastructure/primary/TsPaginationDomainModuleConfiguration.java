package com.seed4j.generator.client.paginationdomain.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;

import com.seed4j.generator.client.paginationdomain.application.TsPaginationDomainApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TsPaginationDomainModuleConfiguration {

  @Bean
  JHipsterModuleResource tsPaginationDomainModule(TsPaginationDomainApplicationService tsPaginationDomain) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.TS_PAGINATION_DOMAIN)
      .withoutProperties()
      .apiDoc("Pagination", "Add webapp domain for pagination")
      .organization(JHipsterModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "webapp", "frontend")
      .factory(tsPaginationDomain::buildModule);
  }
}
