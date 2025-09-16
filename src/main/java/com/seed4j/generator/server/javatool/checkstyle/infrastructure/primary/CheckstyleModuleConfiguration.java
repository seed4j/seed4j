package com.seed4j.generator.server.javatool.checkstyle.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CHECKSTYLE;

import com.seed4j.generator.server.javatool.checkstyle.application.CheckstyleApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CheckstyleModuleConfiguration {

  @Bean
  Seed4JModuleResource checkstyleModule(CheckstyleApplicationService checkstyle) {
    return Seed4JModuleResource.builder()
      .slug(CHECKSTYLE)
      .withoutProperties()
      .apiDoc("Java", "Add Checkstyle configuration to enforce code style rules")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "checkstyle")
      .factory(checkstyle::buildModule);
  }
}
