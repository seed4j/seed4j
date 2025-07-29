package com.seed4j.generator.server.pagination.domainmodel.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PAGINATION_DOMAIN;

import com.seed4j.generator.server.pagination.domainmodel.application.PaginationDomainApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PaginationDomainModuleConfiguration {

  @Bean
  JHipsterModuleResource paginationDomainModule(PaginationDomainApplicationService paginationDomain) {
    return JHipsterModuleResource.builder()
      .slug(PAGINATION_DOMAIN)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add domain model for pagination management")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("server")
      .factory(paginationDomain::buildModule);
  }
}
