package com.seed4j.generator.server.javatool.checkstyle.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CHECKSTYLE;

import com.seed4j.generator.server.javatool.checkstyle.application.CheckstyleApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CheckstyleModuleConfiguration {

  @Bean
  JHipsterModuleResource checkstyleModule(CheckstyleApplicationService checkstyle) {
    return JHipsterModuleResource.builder()
      .slug(CHECKSTYLE)
      .withoutProperties()
      .apiDoc("Java", "Add Checkstyle configuration to enforce code style rules")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "checkstyle")
      .factory(checkstyle::buildModule);
  }
}
