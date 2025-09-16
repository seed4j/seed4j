package com.seed4j.generator.server.pagination.domainmodel.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PAGINATION_DOMAIN;

import com.seed4j.generator.server.pagination.domainmodel.application.PaginationDomainApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PaginationDomainModuleConfiguration {

  @Bean
  Seed4JModuleResource paginationDomainModule(PaginationDomainApplicationService paginationDomain) {
    return Seed4JModuleResource.builder()
      .slug(PAGINATION_DOMAIN)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add domain model for pagination management")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("server")
      .factory(paginationDomain::buildModule);
  }
}
