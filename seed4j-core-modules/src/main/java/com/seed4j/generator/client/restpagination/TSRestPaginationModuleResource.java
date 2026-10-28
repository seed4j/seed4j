package com.seed4j.generator.client.restpagination;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TS_PAGINATION_DOMAIN;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

@AutoService(Seed4JModuleResourceProvider.class)
public class TSRestPaginationModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.TS_REST_PAGINATION)
      .withoutProperties()
      .apiDoc("Pagination", "Add rest pagination to the frontend webapp")
      .organization(Seed4JModuleOrganization.builder().addDependency(TS_PAGINATION_DOMAIN).build())
      .tags("client", "webapp", "frontend")
      .factory(new TSRestPaginationModuleFactory()::buildModule);
  }
}
