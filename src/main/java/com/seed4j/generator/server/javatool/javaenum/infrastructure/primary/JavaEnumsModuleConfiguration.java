package com.seed4j.generator.server.javatool.javaenum.infrastructure.primary;

import com.seed4j.generator.server.javatool.javaenum.application.JavaEnumsApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaEnumsModuleConfiguration {

  @Bean
  JHipsterModuleResource javaEnumsModule(JavaEnumsApplicationService javaEnums) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.JAVA_ENUMS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add simple enums mapper")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteModuleSlug.JAVA_BASE).build())
      .tags("server")
      .factory(javaEnums::buildModule);
  }
}
