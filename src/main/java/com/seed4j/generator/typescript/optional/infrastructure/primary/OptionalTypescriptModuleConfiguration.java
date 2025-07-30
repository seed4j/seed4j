package com.seed4j.generator.typescript.optional.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.OPTIONAL_TYPESCRIPT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.TYPESCRIPT;

import com.seed4j.generator.typescript.optional.application.OptionalTypescriptApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OptionalTypescriptModuleConfiguration {

  @Bean
  JHipsterModuleResource optionalTypescriptModule(OptionalTypescriptApplicationService optionalTypescript) {
    return JHipsterModuleResource.builder()
      .slug(OPTIONAL_TYPESCRIPT)
      .withoutProperties()
      .apiDoc("Typescript", "Add Optional class domain to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(TYPESCRIPT).build())
      .tags("typescript")
      .factory(optionalTypescript::buildModule);
  }
}
