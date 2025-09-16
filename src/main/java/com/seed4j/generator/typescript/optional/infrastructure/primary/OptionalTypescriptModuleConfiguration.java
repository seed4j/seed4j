package com.seed4j.generator.typescript.optional.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.OPTIONAL_TYPESCRIPT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.seed4j.generator.typescript.optional.application.OptionalTypescriptApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OptionalTypescriptModuleConfiguration {

  @Bean
  Seed4JModuleResource optionalTypescriptModule(OptionalTypescriptApplicationService optionalTypescript) {
    return Seed4JModuleResource.builder()
      .slug(OPTIONAL_TYPESCRIPT)
      .withoutProperties()
      .apiDoc("Typescript", "Add Optional class domain to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(TYPESCRIPT).build())
      .tags("typescript")
      .factory(optionalTypescript::buildModule);
  }
}
