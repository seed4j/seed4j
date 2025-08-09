package com.seed4j.generator.server.pagination.jpa.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JPA_PAGINATION;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PAGINATION_DOMAIN;

import com.seed4j.generator.server.pagination.jpa.application.JpaPaginationModuleApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JpaPaginationModuleConfiguration {

  @Bean
  SeedModuleResource jpaPaginationModule(JpaPaginationModuleApplicationService jpaPagination) {
    return SeedModuleResource.builder()
      .slug(JPA_PAGINATION)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add utility class for JPA pagination")
      .organization(SeedModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(JPA_PERSISTENCE).build())
      .tags("server")
      .factory(jpaPagination::buildModule);
  }
}
