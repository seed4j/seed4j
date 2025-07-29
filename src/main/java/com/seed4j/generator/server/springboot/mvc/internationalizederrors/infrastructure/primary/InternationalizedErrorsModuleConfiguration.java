package com.seed4j.generator.server.springboot.mvc.internationalizederrors.infrastructure.primary;

import com.seed4j.generator.server.springboot.mvc.internationalizederrors.application.InternationalizedErrorsApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InternationalizedErrorsModuleConfiguration {

  @Bean
  JHipsterModuleResource internationalizedErrorsModule(InternationalizedErrorsApplicationService internationalizedErrors) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.INTERNATIONALIZED_ERRORS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot", "Add internationalization for application errors")
      .organization(
        JHipsterModuleOrganization.builder()
          .addDependency(JHLiteModuleSlug.JAVA_ENUMS)
          .addDependency(JHLiteModuleSlug.SPRING_BOOT_MVC_EMPTY)
          .build()
      )
      .tags("server", "spring")
      .factory(internationalizedErrors::buildModule);
  }
}
