package com.seed4j.generator.server.springboot.database.jpa.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JPA_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JPA_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JPA_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JPA_POSTGRESQL;

import com.seed4j.generator.server.springboot.database.jpa.application.JpaApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JpaModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  SeedModuleResource jpaPostgreSQLModule(JpaApplicationService postgreSQL) {
    return SeedModuleResource.builder()
      .slug(JPA_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with PostgreSQL to project")
      .organization(SeedModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(tags())
      .factory(postgreSQL::buildPostgreSQL);
  }

  @Bean
  SeedModuleResource jpaMariaDBModule(JpaApplicationService mariaDB) {
    return SeedModuleResource.builder()
      .slug(JPA_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MariaDB to project")
      .organization(SeedModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MARIADB).build())
      .tags(tags())
      .factory(mariaDB::buildMariaDB);
  }

  @Bean
  SeedModuleResource jpaMmySQLModule(JpaApplicationService mySQL) {
    return SeedModuleResource.builder()
      .slug(JPA_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MySQL to project")
      .organization(SeedModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MYSQL).build())
      .tags(tags())
      .factory(mySQL::buildMySQL);
  }

  @Bean
  SeedModuleResource jpaMsSQLModule(JpaApplicationService msSQL) {
    return SeedModuleResource.builder()
      .slug(JPA_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MsSQL to project")
      .organization(SeedModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MSSQL).build())
      .tags(tags())
      .factory(msSQL::buildMsSQL);
  }

  private static SeedModulePropertiesDefinition properties() {
    return SeedModulePropertiesDefinition.builder()
      .addBasePackage()
      .addIndentation()
      .addProjectBaseName()
      .addSpringConfigurationFormat()
      .build();
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "database" };
  }
}
