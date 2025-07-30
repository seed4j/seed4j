package com.seed4j.generator.server.javatool.base.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;

import com.seed4j.generator.server.javatool.base.application.JavaBaseApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaBaseModuleConfiguration {

  @Bean
  JHipsterModuleResource javaBaseModule(JavaBaseApplicationService javaBase) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.JAVA_BASE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Java", "Add Base classes and Error domain to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server")
      .factory(javaBase::buildModule);
  }
}
