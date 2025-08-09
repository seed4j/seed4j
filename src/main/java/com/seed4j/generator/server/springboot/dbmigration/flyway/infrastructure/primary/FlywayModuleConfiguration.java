package com.seed4j.generator.server.springboot.dbmigration.flyway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.DATABASE_MIGRATION;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.FLYWAY;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.FLYWAY_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.FLYWAY_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.FLYWAY_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.FLYWAY_POSTGRESQL;

import com.seed4j.generator.server.springboot.dbmigration.flyway.application.FlywayApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FlywayModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database Migration";

  @Bean
  SeedModuleResource flywayInitializationModule(FlywayApplicationService flyway) {
    return SeedModuleResource.builder()
      .slug(FLYWAY)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc(API_DOC_GROUP, "Add Flyway")
      .organization(SeedModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(DATASOURCE).build())
      .tags(flywayTags())
      .factory(flyway::buildInitializationModule);
  }

  @Bean
  SeedModuleResource flywayMysqlModule(FlywayApplicationService flyway) {
    return SeedModuleResource.builder()
      .slug(FLYWAY_MYSQL)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway MySQL")
      .organization(SeedModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MYSQL).build())
      .tags(flywayTags())
      .factory(flyway::buildMysqlDependencyModule);
  }

  @Bean
  SeedModuleResource flywayMariaDBModule(FlywayApplicationService flyway) {
    return SeedModuleResource.builder()
      .slug(FLYWAY_MARIADB)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway MariaDB")
      .organization(SeedModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MARIADB).build())
      .tags(flywayTags())
      .factory(flyway::buildMysqlDependencyModule);
  }

  @Bean
  SeedModuleResource flywayPostgresModule(FlywayApplicationService flyway) {
    return SeedModuleResource.builder()
      .slug(FLYWAY_POSTGRESQL)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway PostgreSQL")
      .organization(SeedModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(flywayTags())
      .factory(flyway::buildPostgreSQLDependencyModule);
  }

  @Bean
  SeedModuleResource flywayMsSqlServerModule(FlywayApplicationService flyway) {
    return SeedModuleResource.builder()
      .slug(FLYWAY_MSSQL)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway PostgreSQL")
      .organization(SeedModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MSSQL).build())
      .tags(flywayTags())
      .factory(flyway::buildMsSqlServerDependencyModule);
  }

  private String[] flywayTags() {
    return new String[] { "server", "spring", "spring-boot", "database", "migration" };
  }
}
