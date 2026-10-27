package com.seed4j.generator.server.springboot.mvc.sample.liquibase;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_LIQUIBASE_CHANGELOG;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SampleLiquibaseModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_LIQUIBASE_CHANGELOG)
      .withoutProperties()
      .apiDoc("Sample Feature", "Add liquibase changelog for sample feature")
      .organization(
        Seed4JModuleOrganization.builder().feature(SAMPLE_SCHEMA).addDependency(LIQUIBASE).addDependency(SAMPLE_FEATURE).build()
      )
      .tags("server")
      .factory(new SampleLiquibaseModuleFactory()::buildModule);
  }
}
