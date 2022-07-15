package tech.jhipster.lite.generator.server.springboot.database.mssql.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.mssql.application.MsSQLApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class MsSQLModuleConfiguration {

  public static final String URL_MSSQL_MODULE = "/api/servers/spring-boot/databases/mssql";

  @Bean
  JHipsterModuleResource msSQLModule(MsSQLApplicationService applicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(URL_MSSQL_MODULE)
      .slug("mssql")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database", "Add MsSQL to project"))
      .factory(applicationService::build);
  }
}
