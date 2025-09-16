package com.seed4j.generator.server.javatool.memoizer.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_MEMOIZERS;

import com.seed4j.generator.server.javatool.memoizer.application.JavaMemoizersApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaMemoizersModuleConfiguration {

  @Bean
  Seed4JModuleResource javaMemoizersModule(JavaMemoizersApplicationService javaMemoizers) {
    return Seed4JModuleResource.builder()
      .slug(JAVA_MEMOIZERS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add simple memoizers factory")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("server")
      .factory(javaMemoizers::buildModule);
  }
}
