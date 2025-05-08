package tech.jhipster.lite.generator.server.springboot.database.jooq.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.JOOQ;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MARIADB;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MSSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MYSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_POSTGRESQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JOOQ_MARIADB;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JOOQ_MSSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JOOQ_MYSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.JOOQ_POSTGRESQL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.jooq.application.JooqApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class JooqModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  JHipsterModuleResource jooqPostgreSQLModule(JooqApplicationService postgreSQL) {
    return JHipsterModuleResource.builder()
      .slug(JOOQ_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with PostgreSQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_POSTGRESQL).build())
      .tags(tags())
      .factory(postgreSQL::buildPostgreSQL);
  }

  @Bean
  JHipsterModuleResource jooqMariaDBModule(JooqApplicationService mariaDB) {
    return JHipsterModuleResource.builder()
      .slug(JOOQ_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MariaDB to project")
      .organization(JHipsterModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MARIADB).build())
      .tags(tags())
      .factory(mariaDB::buildMariaDB);
  }

  @Bean
  JHipsterModuleResource jooqMySQLModule(JooqApplicationService mySQL) {
    return JHipsterModuleResource.builder()
      .slug(JOOQ_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MySQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MYSQL).build())
      .tags(tags())
      .factory(mySQL::buildMySQL);
  }

  @Bean
  JHipsterModuleResource jooqMsSQLModule(JooqApplicationService msSQL) {
    return JHipsterModuleResource.builder()
      .slug(JOOQ_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add Jooq with MsSQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JOOQ).addDependency(DATASOURCE_MSSQL).build())
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
