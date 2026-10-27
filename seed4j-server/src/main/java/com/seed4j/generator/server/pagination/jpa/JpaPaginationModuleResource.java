package com.seed4j.generator.server.pagination.jpa;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_PAGINATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PAGINATION_DOMAIN;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class JpaPaginationModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(JPA_PAGINATION)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add utility class for JPA pagination")
      .organization(Seed4JModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(JPA_PERSISTENCE).build())
      .tags("server")
      .factory(new JpaPaginationModuleFactory()::buildModule);
  }
}
