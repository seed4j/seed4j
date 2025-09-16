package com.seed4j.generator.server.pagination.rest.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PAGINATION_DOMAIN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REST_PAGINATION;

import com.seed4j.generator.server.pagination.rest.application.RestPaginationModuleApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestPaginationModuleConfiguration {

  @Bean
  Seed4JModuleResource restPaginationModule(RestPaginationModuleApplicationService restPagination) {
    return Seed4JModuleResource.builder()
      .slug(REST_PAGINATION)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add rest models for pagination handling")
      .organization(Seed4JModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(SPRINGDOC).build())
      .tags("server")
      .factory(restPagination::buildModule);
  }
}
