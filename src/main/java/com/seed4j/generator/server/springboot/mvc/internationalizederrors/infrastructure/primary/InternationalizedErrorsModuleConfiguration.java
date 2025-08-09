package com.seed4j.generator.server.springboot.mvc.internationalizederrors.infrastructure.primary;

import com.seed4j.generator.server.springboot.mvc.internationalizederrors.application.InternationalizedErrorsApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.Seed4JModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InternationalizedErrorsModuleConfiguration {

  @Bean
  SeedModuleResource internationalizedErrorsModule(InternationalizedErrorsApplicationService internationalizedErrors) {
    return SeedModuleResource.builder()
      .slug(Seed4JModuleSlug.INTERNATIONALIZED_ERRORS)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot", "Add internationalization for application errors")
      .organization(
        SeedModuleOrganization.builder()
          .addDependency(Seed4JModuleSlug.JAVA_ENUMS)
          .addDependency(Seed4JModuleSlug.SPRING_BOOT_MVC_EMPTY)
          .build()
      )
      .tags("server", "spring")
      .factory(internationalizedErrors::buildModule);
  }
}
