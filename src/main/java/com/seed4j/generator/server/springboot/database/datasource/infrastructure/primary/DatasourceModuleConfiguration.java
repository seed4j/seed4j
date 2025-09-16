package com.seed4j.generator.server.springboot.database.datasource.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.database.datasource.application.DatasourceApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DatasourceModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  Seed4JModuleResource postgreSQLDatasourceModule(DatasourceApplicationService datasource) {
    return Seed4JModuleResource.builder()
      .slug(DATASOURCE_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add PostgreSQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildPostgreSQL);
  }

  @Bean
  Seed4JModuleResource mariaDBDatasourceModule(DatasourceApplicationService datasource) {
    return Seed4JModuleResource.builder()
      .slug(DATASOURCE_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MariaDB datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMariaDB);
  }

  @Bean
  Seed4JModuleResource mmySQLDatasourceModule(DatasourceApplicationService datasource) {
    return Seed4JModuleResource.builder()
      .slug(DATASOURCE_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MySQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMySQL);
  }

  @Bean
  Seed4JModuleResource msSQLDatasourceModule(DatasourceApplicationService datasource) {
    return Seed4JModuleResource.builder()
      .slug(DATASOURCE_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MsSQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMsSQL);
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder().addIndentation().addProjectBaseName().addSpringConfigurationFormat().build();
  }

  private static Seed4JModuleOrganization organization() {
    return Seed4JModuleOrganization.builder().feature(DATASOURCE).addDependency(SPRING_BOOT).build();
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "datasource", "database" };
  }
}
