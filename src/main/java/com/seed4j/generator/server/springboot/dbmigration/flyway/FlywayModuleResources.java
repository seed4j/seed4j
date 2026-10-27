package com.seed4j.generator.server.springboot.dbmigration.flyway;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATABASE_MIGRATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FLYWAY_POSTGRESQL;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class FlywayModuleResources {

  private static final String API_DOC_GROUP = "Spring Boot - Database Migration";

  private FlywayModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Flyway implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FLYWAY)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
        )
        .apiDoc(API_DOC_GROUP, "Add Flyway")
        .organization(Seed4JModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(DATASOURCE).build())
        .tags(flywayTags())
        .factory(new FlywayModuleFactory()::buildInitializationModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mysql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FLYWAY_MYSQL)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
        .apiDoc(API_DOC_GROUP, "Add Flyway MySQL")
        .organization(Seed4JModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MYSQL).build())
        .tags(flywayTags())
        .factory(new FlywayModuleFactory()::buildMysqlDependencyModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mariadb implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FLYWAY_MARIADB)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
        .apiDoc(API_DOC_GROUP, "Add Flyway MariaDB")
        .organization(Seed4JModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MARIADB).build())
        .tags(flywayTags())
        .factory(new FlywayModuleFactory()::buildMysqlDependencyModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Postgresql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FLYWAY_POSTGRESQL)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
        .apiDoc(API_DOC_GROUP, "Add Flyway PostgreSQL")
        .organization(Seed4JModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_POSTGRESQL).build())
        .tags(flywayTags())
        .factory(new FlywayModuleFactory()::buildPostgreSQLDependencyModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mssql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FLYWAY_MSSQL)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
        .apiDoc(API_DOC_GROUP, "Add Flyway PostgreSQL")
        .organization(Seed4JModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MSSQL).build())
        .tags(flywayTags())
        .factory(new FlywayModuleFactory()::buildMsSqlServerDependencyModule);
    }
  }

  private static String[] flywayTags() {
    return new String[] { "server", "spring", "spring-boot", "database", "migration" };
  }
}
