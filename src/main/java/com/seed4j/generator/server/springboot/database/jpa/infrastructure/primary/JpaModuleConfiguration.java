package com.seed4j.generator.server.springboot.database.jpa.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JPA_MARIADB;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JPA_MSSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JPA_MYSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JPA_POSTGRESQL;

import com.seed4j.generator.server.springboot.database.jpa.application.JpaApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JpaModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  JHipsterModuleResource jpaPostgreSQLModule(JpaApplicationService postgreSQL) {
    return JHipsterModuleResource.builder()
      .slug(JPA_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with PostgreSQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(tags())
      .factory(postgreSQL::buildPostgreSQL);
  }

  @Bean
  JHipsterModuleResource jpaMariaDBModule(JpaApplicationService mariaDB) {
    return JHipsterModuleResource.builder()
      .slug(JPA_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MariaDB to project")
      .organization(JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MARIADB).build())
      .tags(tags())
      .factory(mariaDB::buildMariaDB);
  }

  @Bean
  JHipsterModuleResource jpaMmySQLModule(JpaApplicationService mySQL) {
    return JHipsterModuleResource.builder()
      .slug(JPA_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MySQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MYSQL).build())
      .tags(tags())
      .factory(mySQL::buildMySQL);
  }

  @Bean
  JHipsterModuleResource jpaMsSQLModule(JpaApplicationService msSQL) {
    return JHipsterModuleResource.builder()
      .slug(JPA_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MsSQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MSSQL).build())
      .tags(tags())
      .factory(msSQL::buildMsSQL);
  }

  private static JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder()
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
