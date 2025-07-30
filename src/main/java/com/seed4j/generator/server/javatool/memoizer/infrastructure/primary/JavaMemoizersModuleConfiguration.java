package com.seed4j.generator.server.javatool.memoizer.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JAVA_MEMOIZERS;

import com.seed4j.generator.server.javatool.memoizer.application.JavaMemoizersApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JavaMemoizersModuleConfiguration {

  @Bean
  JHipsterModuleResource javaMemoizersModule(JavaMemoizersApplicationService javaMemoizers) {
    return JHipsterModuleResource.builder()
      .slug(JAVA_MEMOIZERS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add simple memoizers factory")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("server")
      .factory(javaMemoizers::buildModule);
  }
}
