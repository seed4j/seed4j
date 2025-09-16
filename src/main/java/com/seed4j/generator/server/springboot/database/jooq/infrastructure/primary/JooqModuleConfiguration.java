package com.seed4j.generator.server.springboot.database.jooq.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JOOQ;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JOOQ_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JOOQ_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JOOQ_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JOOQ_POSTGRESQL;

import com.seed4j.generator.server.springboot.database.jooq.application.JooqApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JooqModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  Seed4JModuleResource jooqPostgreSQLModule(JooqApplicationService postgreSQL) {
    return Seed4JModuleResource.builder()
      .slug(JOOQ_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with PostgreSQL to project")
      .organization(Seed4JModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(tags())
      .factory(postgreSQL::buildPostgreSQL);
  }

  @Bean
  Seed4JModuleResource jooqMariaDBModule(JooqApplicationService mariaDB) {
    return Seed4JModuleResource.builder()
      .slug(JOOQ_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MariaDB to project")
      .organization(Seed4JModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MARIADB).build())
      .tags(tags())
      .factory(mariaDB::buildMariaDB);
  }

  @Bean
  Seed4JModuleResource jooqMySQLModule(JooqApplicationService mySQL) {
    return Seed4JModuleResource.builder()
      .slug(JOOQ_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MySQL to project")
      .organization(Seed4JModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MYSQL).build())
      .tags(tags())
      .factory(mySQL::buildMySQL);
  }

  @Bean
  Seed4JModuleResource jooqMsSQLModule(JooqApplicationService msSQL) {
    return Seed4JModuleResource.builder()
      .slug(JOOQ_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MsSQL to project")
      .organization(Seed4JModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MSSQL).build())
      .tags(tags())
      .factory(msSQL::buildMsSQL);
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder()
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
