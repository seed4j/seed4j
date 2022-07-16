package tech.jhipster.lite.generator.server.springboot.database.mysql.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class MySQLModuleConfiguration {

  public static final String URL_MYSQL_MODULE = "/api/servers/spring-boot/databases/mysql";

  @Bean
  JHipsterModuleResource mySQLModule(MySQLApplicationService applicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(URL_MYSQL_MODULE)
      .slug("mysql")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database", "Add MySQL to project"))
      .tags("server", "spring", "spring-boot", "database")
      .factory(applicationService::build);
  }
}
