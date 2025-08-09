package com.seed4j.generator.typescript.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.TYPESCRIPT;

import com.seed4j.generator.typescript.core.application.TypescriptApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TypescriptModuleConfiguration {

  @Bean
  SeedModuleResource typescriptModule(TypescriptApplicationService typescript) {
    return SeedModuleResource.builder()
      .slug(TYPESCRIPT)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Typescript", "Init Typescript project")
      .organization(SeedModuleOrganization.builder().addDependency(INIT).addDependency(PRETTIER).build())
      .tags("typescript")
      .factory(typescript::buildModule);
  }

  private SeedModulePropertiesDefinition propertiesDefinition() {
    return SeedModulePropertiesDefinition.builder().addNodePackageManager().build();
  }
}
