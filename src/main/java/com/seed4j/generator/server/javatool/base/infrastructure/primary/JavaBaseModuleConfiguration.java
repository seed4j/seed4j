package com.seed4j.generator.server.javatool.base.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;

import com.seed4j.generator.server.javatool.base.application.JavaBaseApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaBaseModuleConfiguration {

  @Bean
  SeedModuleResource javaBaseModule(JavaBaseApplicationService javaBase) {
    return SeedModuleResource.builder()
      .slug(JHLiteModuleSlug.JAVA_BASE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Java", "Add Base classes and Error domain to project")
      .organization(SeedModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server")
      .factory(javaBase::buildModule);
  }
}
