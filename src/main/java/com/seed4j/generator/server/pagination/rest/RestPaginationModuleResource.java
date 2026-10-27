package com.seed4j.generator.server.pagination.rest;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PAGINATION_DOMAIN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REST_PAGINATION;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class RestPaginationModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(REST_PAGINATION)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add rest models for pagination handling")
      .organization(Seed4JModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(SPRINGDOC).build())
      .tags("server")
      .factory(new RestPaginationModuleFactory()::buildModule);
  }
}
