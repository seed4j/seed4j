package com.seed4j.generator.server.pagination.domainmodel.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PAGINATION_DOMAIN;

import com.seed4j.generator.server.pagination.domainmodel.application.PaginationDomainApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PaginationDomainModuleConfiguration {

  @Bean
  SeedModuleResource paginationDomainModule(PaginationDomainApplicationService paginationDomain) {
    return SeedModuleResource.builder()
      .slug(PAGINATION_DOMAIN)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add domain model for pagination management")
      .organization(SeedModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("server")
      .factory(paginationDomain::buildModule);
  }
}
