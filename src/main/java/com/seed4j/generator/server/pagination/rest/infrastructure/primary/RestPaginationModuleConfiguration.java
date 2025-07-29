package com.seed4j.generator.server.pagination.rest.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PAGINATION_DOMAIN;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REST_PAGINATION;

import com.seed4j.generator.server.pagination.rest.application.RestPaginationModuleApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestPaginationModuleConfiguration {

  @Bean
  JHipsterModuleResource restPaginationModule(RestPaginationModuleApplicationService restPagination) {
    return JHipsterModuleResource.builder()
      .slug(REST_PAGINATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add rest models for pagination handling")
      .organization(JHipsterModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(SPRINGDOC).build())
      .tags("server")
      .factory(restPagination::buildModule);
  }
}
