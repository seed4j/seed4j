package com.seed4j.generator.server.javatool.base.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;

import com.seed4j.generator.server.javatool.base.application.JavaBaseApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaBaseModuleConfiguration {

  @Bean
  Seed4JModuleResource javaBaseModule(JavaBaseApplicationService javaBase) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.JAVA_BASE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Java", "Add Base classes and Error domain to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server")
      .factory(javaBase::buildModule);
  }
}
