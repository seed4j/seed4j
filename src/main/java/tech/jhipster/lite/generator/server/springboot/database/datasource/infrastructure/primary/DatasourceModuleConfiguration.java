package tech.jhipster.lite.generator.server.springboot.database.datasource.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.DATASOURCE;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MARIADB;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MSSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_MYSQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.DATASOURCE_POSTGRESQL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.datasource.application.DatasourceApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class DatasourceModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  JHipsterModuleResource postgreSQLDatasourceModule(DatasourceApplicationService datasource) {
    return JHipsterModuleResource.builder()
      .slug(DATASOURCE_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add PostgreSQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildPostgreSQL);
  }

  @Bean
  JHipsterModuleResource mariaDBDatasourceModule(DatasourceApplicationService datasource) {
    return JHipsterModuleResource.builder()
      .slug(DATASOURCE_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MariaDB datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMariaDB);
  }

  @Bean
  JHipsterModuleResource mmySQLDatasourceModule(DatasourceApplicationService datasource) {
    return JHipsterModuleResource.builder()
      .slug(DATASOURCE_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MySQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMySQL);
  }

  @Bean
  JHipsterModuleResource msSQLDatasourceModule(DatasourceApplicationService datasource) {
    return JHipsterModuleResource.builder()
      .slug(DATASOURCE_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MsSQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMsSQL);
  }

  private static JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addIndentation().addProjectBaseName().addSpringConfigurationFormat().build();
  }

  private static JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature(DATASOURCE).addDependency(SPRING_BOOT).build();
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "datasource", "database" };
  }
}
