package com.seed4j.generator.server.javatool.memoizer.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JAVA_MEMOIZERS;

import com.seed4j.generator.server.javatool.memoizer.application.JavaMemoizersApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaMemoizersModuleConfiguration {

  @Bean
  SeedModuleResource javaMemoizersModule(JavaMemoizersApplicationService javaMemoizers) {
    return SeedModuleResource.builder()
      .slug(JAVA_MEMOIZERS)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add simple memoizers factory")
      .organization(SeedModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("server")
      .factory(javaMemoizers::buildModule);
  }
}
