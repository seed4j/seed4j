package com.seed4j.generator.client.restpagination.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.TS_PAGINATION_DOMAIN;

import com.seed4j.generator.client.restpagination.application.TSRestPaginationApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TSRestPaginationModuleConfiguration {

  @Bean
  SeedModuleResource tsRestPaginationModule(TSRestPaginationApplicationService tsRestPagination) {
    return SeedModuleResource.builder()
      .slug(JHLiteModuleSlug.TS_REST_PAGINATION)
      .withoutProperties()
      .apiDoc("Pagination", "Add rest pagination to the frontend webapp")
      .organization(SeedModuleOrganization.builder().addDependency(TS_PAGINATION_DOMAIN).build())
      .tags("client", "webapp", "frontend")
      .factory(tsRestPagination::buildModule);
  }
}
