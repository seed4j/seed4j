package tech.jhipster.lite.generator.server.springboot.mvc.internationalized_errors.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.JHLiteFeatureSlug;
import tech.jhipster.lite.generator.JHLiteModuleSlug;
import tech.jhipster.lite.generator.server.springboot.mvc.internationalized_errors.application.InternationalizedErrorsApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class InternationalizedErrorsModuleConfiguration {

  @Bean
  JHipsterModuleResource internationalizedErrorsModule(InternationalizedErrorsApplicationService internationalizedErrors) {
    return JHipsterModuleResource
      .builder()
      .slug(JHLiteModuleSlug.INTERNATIONALIZED_ERRORS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot", "Add internationalization for application errors")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .addDependency(JHLiteModuleSlug.JAVA_ENUMS)
          .addDependency(JHLiteFeatureSlug.SPRING_SERVER)
          .build()
      )
      .tags("server", "spring")
      .factory(internationalizedErrors::buildModule);
  }
}
