package com.seed4j.generator.server.springboot.database.jooq.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JOOQ;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JOOQ_MARIADB;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JOOQ_MSSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JOOQ_MYSQL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JOOQ_POSTGRESQL;

import com.seed4j.generator.server.springboot.database.jooq.application.JooqApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JooqModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  SeedModuleResource jooqPostgreSQLModule(JooqApplicationService postgreSQL) {
    return SeedModuleResource.builder()
      .slug(JOOQ_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with PostgreSQL to project")
      .organization(SeedModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(tags())
      .factory(postgreSQL::buildPostgreSQL);
  }

  @Bean
  SeedModuleResource jooqMariaDBModule(JooqApplicationService mariaDB) {
    return SeedModuleResource.builder()
      .slug(JOOQ_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MariaDB to project")
      .organization(SeedModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MARIADB).build())
      .tags(tags())
      .factory(mariaDB::buildMariaDB);
  }

  @Bean
  SeedModuleResource jooqMySQLModule(JooqApplicationService mySQL) {
    return SeedModuleResource.builder()
      .slug(JOOQ_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MySQL to project")
      .organization(SeedModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MYSQL).build())
      .tags(tags())
      .factory(mySQL::buildMySQL);
  }

  @Bean
  SeedModuleResource jooqMsSQLModule(JooqApplicationService msSQL) {
    return SeedModuleResource.builder()
      .slug(JOOQ_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MsSQL to project")
      .organization(SeedModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MSSQL).build())
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
