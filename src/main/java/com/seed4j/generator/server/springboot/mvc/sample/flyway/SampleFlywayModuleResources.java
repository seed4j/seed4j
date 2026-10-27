package com.seed4j.generator.server.springboot.mvc.sample.flyway;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_NOT_POSTGRESQL_FLYWAY_CHANGELOG;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_POSTGRESQL_FLYWAY_CHANGELOG;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class SampleFlywayModuleResources {

  private SampleFlywayModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class PostgresqlFlywayChangelog implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SAMPLE_POSTGRESQL_FLYWAY_CHANGELOG)
        .withoutProperties()
        .apiDoc("Sample Feature", "Add PostgreSQL flyway changelog for sample feature")
        .organization(
          Seed4JModuleOrganization.builder()
            .feature(SAMPLE_SCHEMA)
            .addDependency(FLYWAY_POSTGRESQL)
            .addDependency(SAMPLE_FEATURE)
            .addDependency(JPA_POSTGRESQL)
            .build()
        )
        .tags("server")
        .factory(new SampleFlywayModuleFactory()::buildPostgreSQLModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class NotPostgresqlFlywayChangelog implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SAMPLE_NOT_POSTGRESQL_FLYWAY_CHANGELOG)
        .withoutProperties()
        .apiDoc("Sample Feature", "Add not PostgreSQL flyway changelog for sample feature")
        .organization(Seed4JModuleOrganization.builder().feature(SAMPLE_SCHEMA).addDependency(FLYWAY).addDependency(SAMPLE_FEATURE).build())
        .tags("server")
        .factory(new SampleFlywayModuleFactory()::buildNotPostgreSQLModule);
    }
  }
}
