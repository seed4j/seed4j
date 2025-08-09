package com.seed4j.generator.server.javatool.javaenum.infrastructure.primary;

import com.seed4j.generator.server.javatool.javaenum.application.JavaEnumsApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.Seed4JModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaEnumsModuleConfiguration {

  @Bean
  SeedModuleResource javaEnumsModule(JavaEnumsApplicationService javaEnums) {
    return SeedModuleResource.builder()
      .slug(Seed4JModuleSlug.JAVA_ENUMS)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add simple enums mapper")
      .organization(SeedModuleOrganization.builder().addDependency(Seed4JModuleSlug.JAVA_BASE).build())
      .tags("server")
      .factory(javaEnums::buildModule);
  }
}
