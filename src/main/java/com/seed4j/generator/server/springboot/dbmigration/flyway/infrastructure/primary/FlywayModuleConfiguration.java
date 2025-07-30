package com.seed4j.generator.server.springboot.dbmigration.flyway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.DATABASE_MIGRATION;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FLYWAY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FLYWAY_MARIADB;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FLYWAY_MSSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FLYWAY_MYSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FLYWAY_POSTGRESQL;

import com.seed4j.generator.server.springboot.dbmigration.flyway.application.FlywayApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FlywayModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database Migration";

  @Bean
  JHipsterModuleResource flywayInitializationModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource.builder()
      .slug(FLYWAY)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc(API_DOC_GROUP, "Add Flyway")
      .organization(JHipsterModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(DATASOURCE).build())
      .tags(flywayTags())
      .factory(flyway::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource flywayMysqlModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource.builder()
      .slug(FLYWAY_MYSQL)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway MySQL")
      .organization(JHipsterModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MYSQL).build())
      .tags(flywayTags())
      .factory(flyway::buildMysqlDependencyModule);
  }

  @Bean
  JHipsterModuleResource flywayMariaDBModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource.builder()
      .slug(FLYWAY_MARIADB)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway MariaDB")
      .organization(JHipsterModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MARIADB).build())
      .tags(flywayTags())
      .factory(flyway::buildMysqlDependencyModule);
  }

  @Bean
  JHipsterModuleResource flywayPostgresModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource.builder()
      .slug(FLYWAY_POSTGRESQL)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway PostgreSQL")
      .organization(JHipsterModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(flywayTags())
      .factory(flyway::buildPostgreSQLDependencyModule);
  }

  @Bean
  JHipsterModuleResource flywayMsSqlServerModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource.builder()
      .slug(FLYWAY_MSSQL)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(API_DOC_GROUP, "Add Flyway PostgreSQL")
      .organization(JHipsterModuleOrganization.builder().addDependency(FLYWAY).addDependency(DATASOURCE_MSSQL).build())
      .tags(flywayTags())
      .factory(flyway::buildMsSqlServerDependencyModule);
  }

  private String[] flywayTags() {
    return new String[] { "server", "spring", "spring-boot", "database", "migration" };
  }
}
