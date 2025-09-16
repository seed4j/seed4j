package com.seed4j.generator.server.springboot.mvc.internationalizederrors.infrastructure.primary;

import com.seed4j.generator.server.springboot.mvc.internationalizederrors.application.InternationalizedErrorsApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InternationalizedErrorsModuleConfiguration {

  @Bean
  Seed4JModuleResource internationalizedErrorsModule(InternationalizedErrorsApplicationService internationalizedErrors) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.INTERNATIONALIZED_ERRORS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot", "Add internationalization for application errors")
      .organization(
        Seed4JModuleOrganization.builder()
          .addDependency(Seed4JCoreModuleSlug.JAVA_ENUMS)
          .addDependency(Seed4JCoreModuleSlug.SPRING_BOOT_MVC_EMPTY)
          .build()
      )
      .tags("server", "spring")
      .factory(internationalizedErrors::buildModule);
  }
}
