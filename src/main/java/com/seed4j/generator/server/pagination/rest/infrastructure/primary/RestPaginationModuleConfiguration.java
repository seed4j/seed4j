package com.seed4j.generator.server.pagination.rest.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PAGINATION_DOMAIN;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.REST_PAGINATION;

import com.seed4j.generator.server.pagination.rest.application.RestPaginationModuleApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestPaginationModuleConfiguration {

  @Bean
  SeedModuleResource restPaginationModule(RestPaginationModuleApplicationService restPagination) {
    return SeedModuleResource.builder()
      .slug(REST_PAGINATION)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add rest models for pagination handling")
      .organization(SeedModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(SPRINGDOC).build())
      .tags("server")
      .factory(restPagination::buildModule);
  }
}
