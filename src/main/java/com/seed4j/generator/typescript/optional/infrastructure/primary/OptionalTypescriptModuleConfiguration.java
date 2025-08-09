package com.seed4j.generator.typescript.optional.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.OPTIONAL_TYPESCRIPT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.TYPESCRIPT;

import com.seed4j.generator.typescript.optional.application.OptionalTypescriptApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OptionalTypescriptModuleConfiguration {

  @Bean
  SeedModuleResource optionalTypescriptModule(OptionalTypescriptApplicationService optionalTypescript) {
    return SeedModuleResource.builder()
      .slug(OPTIONAL_TYPESCRIPT)
      .withoutProperties()
      .apiDoc("Typescript", "Add Optional class domain to project")
      .organization(SeedModuleOrganization.builder().addDependency(TYPESCRIPT).build())
      .tags("typescript")
      .factory(optionalTypescript::buildModule);
  }
}
