package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.application.SpringdocApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class SpringdocModuleConfiguration {

  private static final String SPRINGDOC_API_URL = "/api/servers/spring-boot/api-documentations/springdoc";

  @Bean
  JHipsterModuleResource springdocModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(SPRINGDOC_API_URL + "/init")
      .slug("springdoc-openapi")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - API Documentation", "Add springdoc-openapi"))
      .tags("server", "spring", "spring-boot", "documentation")
      .factory(springdocApplicationService::buildSpringdocModule);
  }

  @Bean
  JHipsterModuleResource springdocWithSecurityJwtModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(SPRINGDOC_API_URL + "/init-with-security-jwt")
      .slug("springdoc-openapi-with-security-jwt")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - API Documentation - Security", "Add springdoc-openapi with Security JWT"))
      .tags("server", "spring", "spring-boot", "documentation")
      .factory(springdocApplicationService::buildSpringdocModuleWithSecurityJWT);
  }

  private JHipsterModulePropertiesDefinition buildPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build();
  }
}
