package com.seed4j.generator.server.pagination.jpa.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_PAGINATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PAGINATION_DOMAIN;

import com.seed4j.generator.server.pagination.jpa.application.JpaPaginationModuleApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JpaPaginationModuleConfiguration {

  @Bean
  Seed4JModuleResource jpaPaginationModule(JpaPaginationModuleApplicationService jpaPagination) {
    return Seed4JModuleResource.builder()
      .slug(JPA_PAGINATION)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add utility class for JPA pagination")
      .organization(Seed4JModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(JPA_PERSISTENCE).build())
      .tags("server")
      .factory(jpaPagination::buildModule);
  }
}
