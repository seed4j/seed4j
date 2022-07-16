package tech.jhipster.lite.generator.server.springboot.database.postgresql.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.application.PostgresqlApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class PostgresqlModuleConfiguration {

  public static final String URL_POSTGRESQL_MODULE = "/api/servers/spring-boot/databases/postgresql";

  @Bean
  JHipsterModuleResource postgresqlModule(PostgresqlApplicationService postgresql) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(URL_POSTGRESQL_MODULE)
      .slug("postgresql")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database", "Add Postgresql to project"))
      .tags("server", "spring", "spring-boot", "database")
      .factory(postgresql::build);
  }
}
