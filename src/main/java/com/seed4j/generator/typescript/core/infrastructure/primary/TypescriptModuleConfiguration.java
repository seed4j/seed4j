package com.seed4j.generator.typescript.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.TYPESCRIPT;

import com.seed4j.generator.typescript.core.application.TypescriptApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TypescriptModuleConfiguration {

  @Bean
  JHipsterModuleResource typescriptModule(TypescriptApplicationService typescript) {
    return JHipsterModuleResource.builder()
      .slug(TYPESCRIPT)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Typescript", "Init Typescript project")
      .organization(JHipsterModuleOrganization.builder().addDependency(INIT).addDependency(PRETTIER).build())
      .tags("typescript")
      .factory(typescript::buildModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addNodePackageManager().build();
  }
}
