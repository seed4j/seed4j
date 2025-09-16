package com.seed4j.generator.server.javatool.javaenum.infrastructure.primary;

import com.seed4j.generator.server.javatool.javaenum.application.JavaEnumsApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaEnumsModuleConfiguration {

  @Bean
  Seed4JModuleResource javaEnumsModule(JavaEnumsApplicationService javaEnums) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.JAVA_ENUMS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add simple enums mapper")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreModuleSlug.JAVA_BASE).build())
      .tags("server")
      .factory(javaEnums::buildModule);
  }
}
