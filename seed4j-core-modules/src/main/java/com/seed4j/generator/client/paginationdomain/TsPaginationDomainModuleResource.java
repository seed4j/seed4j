package com.seed4j.generator.client.paginationdomain;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

@AutoService(Seed4JModuleResourceProvider.class)
public class TsPaginationDomainModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.TS_PAGINATION_DOMAIN)
      .withoutProperties()
      .apiDoc("Pagination", "Add webapp domain for pagination")
      .organization(Seed4JModuleOrganization.builder().addDependency(CLIENT_CORE).build())
      .tags("client", "webapp", "frontend")
      .factory(new TSPaginationDomainModuleFactory()::buildModule);
  }
}
