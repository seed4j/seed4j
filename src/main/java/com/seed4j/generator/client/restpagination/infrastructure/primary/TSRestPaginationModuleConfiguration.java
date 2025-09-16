package com.seed4j.generator.client.restpagination.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TS_PAGINATION_DOMAIN;

import com.seed4j.generator.client.restpagination.application.TSRestPaginationApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TSRestPaginationModuleConfiguration {

  @Bean
  Seed4JModuleResource tsRestPaginationModule(TSRestPaginationApplicationService tsRestPagination) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.TS_REST_PAGINATION)
      .withoutProperties()
      .apiDoc("Pagination", "Add rest pagination to the frontend webapp")
      .organization(Seed4JModuleOrganization.builder().addDependency(TS_PAGINATION_DOMAIN).build())
      .tags("client", "webapp", "frontend")
      .factory(tsRestPagination::buildModule);
  }
}
