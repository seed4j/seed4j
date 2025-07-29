package com.seed4j.generator.server.pagination.jpa.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JPA_PAGINATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PAGINATION_DOMAIN;

import com.seed4j.generator.server.pagination.jpa.application.JpaPaginationModuleApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JpaPaginationModuleConfiguration {

  @Bean
  JHipsterModuleResource jpaPaginationModule(JpaPaginationModuleApplicationService jpaPagination) {
    return JHipsterModuleResource.builder()
      .slug(JPA_PAGINATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add utility class for JPA pagination")
      .organization(JHipsterModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(JPA_PERSISTENCE).build())
      .tags("server")
      .factory(jpaPagination::buildModule);
  }
}
