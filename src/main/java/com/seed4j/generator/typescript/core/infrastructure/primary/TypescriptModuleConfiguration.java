package com.seed4j.generator.typescript.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.seed4j.generator.typescript.core.application.TypescriptApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TypescriptModuleConfiguration {

  @Bean
  Seed4JModuleResource typescriptModule(TypescriptApplicationService typescript) {
    return Seed4JModuleResource.builder()
      .slug(TYPESCRIPT)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Typescript", "Init Typescript project")
      .organization(Seed4JModuleOrganization.builder().addDependency(INIT).addDependency(PRETTIER).build())
      .tags("typescript")
      .factory(typescript::buildModule);
  }

  private Seed4JModulePropertiesDefinition propertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder().addNodePackageManager().build();
  }
}
