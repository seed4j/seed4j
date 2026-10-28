package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CASSANDRA_MIGRATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_CASSANDRA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SampleCassandraPersistenceModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_CASSANDRA_PERSISTENCE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add Cassandra persistence for sample feature")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(SAMPLE_PERSISTENCE)
          .addDependency(SAMPLE_FEATURE)
          .addDependency(CASSANDRA_MIGRATION)
          .build()
      )
      .tags("server")
      .factory(new SampleCassandraPersistenceModuleFactory()::buildModule);
  }
}
