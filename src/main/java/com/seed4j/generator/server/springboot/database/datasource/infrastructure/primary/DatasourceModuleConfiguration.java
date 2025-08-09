package com.seed4j.generator.server.springboot.database.datasource.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.database.datasource.application.DatasourceApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DatasourceModuleConfiguration {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  @Bean
  SeedModuleResource postgreSQLDatasourceModule(DatasourceApplicationService datasource) {
    return SeedModuleResource.builder()
      .slug(DATASOURCE_POSTGRESQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add PostgreSQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildPostgreSQL);
  }

  @Bean
  SeedModuleResource mariaDBDatasourceModule(DatasourceApplicationService datasource) {
    return SeedModuleResource.builder()
      .slug(DATASOURCE_MARIADB)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MariaDB datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMariaDB);
  }

  @Bean
  SeedModuleResource mmySQLDatasourceModule(DatasourceApplicationService datasource) {
    return SeedModuleResource.builder()
      .slug(DATASOURCE_MYSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MySQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMySQL);
  }

  @Bean
  SeedModuleResource msSQLDatasourceModule(DatasourceApplicationService datasource) {
    return SeedModuleResource.builder()
      .slug(DATASOURCE_MSSQL)
      .propertiesDefinition(properties())
      .apiDoc(API_DOC_GROUP, "Add MsSQL datasource to Spring project")
      .organization(organization())
      .tags(tags())
      .factory(datasource::buildMsSQL);
  }

  private static SeedModulePropertiesDefinition properties() {
    return SeedModulePropertiesDefinition.builder().addIndentation().addProjectBaseName().addSpringConfigurationFormat().build();
  }

  private static SeedModuleOrganization organization() {
    return SeedModuleOrganization.builder().feature(DATASOURCE).addDependency(SPRING_BOOT).build();
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "datasource", "database" };
  }
}
