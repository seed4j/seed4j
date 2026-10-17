package com.seed4j.generator.server.springboot.database.jpa.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_METAMODEL_GENERATOR;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_POSTGRESQL;

import com.seed4j.generator.server.springboot.database.jpa.application.JpaApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JpaModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  Seed4JModuleResource jpaPostgreSQLModule(JpaApplicationService postgreSQL) {
    return Seed4JModuleResource.builder()
      .slug(JPA_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with PostgreSQL to project")
      .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(tags())
      .factory(postgreSQL::buildPostgreSQL);
  }

  @Bean
  Seed4JModuleResource jpaMariaDBModule(JpaApplicationService mariaDB) {
    return Seed4JModuleResource.builder()
      .slug(JPA_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MariaDB to project")
      .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MARIADB).build())
      .tags(tags())
      .factory(mariaDB::buildMariaDB);
  }

  @Bean
  Seed4JModuleResource jpaMySQLModule(JpaApplicationService mySQL) {
    return Seed4JModuleResource.builder()
      .slug(JPA_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MySQL to project")
      .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MYSQL).build())
      .tags(tags())
      .factory(mySQL::buildMySQL);
  }

  @Bean
  Seed4JModuleResource jpaMsSQLModule(JpaApplicationService msSQL) {
    return Seed4JModuleResource.builder()
      .slug(JPA_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add JPA with MsSQL to project")
      .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MSSQL).build())
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

  @Bean
  Seed4JModuleResource metaModelGeneratorModule(JpaApplicationService jpa) {
    return Seed4JModuleResource.builder()
      .slug(JPA_METAMODEL_GENERATOR)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.EMPTY)
      .apiDoc(API_DOC_GROUP, "Add JPA metamodel generator to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(JPA_PERSISTENCE).build())
      .tags(tags())
      .factory(jpa::buildMetaModelGenerator);
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "database" };
  }
}
