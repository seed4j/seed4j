package tech.jhipster.lite.generator.server.javatool.checkstyle.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.checkstyle.application.CheckstyleApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CheckstyleModuleConfiguration {

  @Bean
  JHipsterModuleResource checkstyleModule(CheckstyleApplicationService checkstyle) {
    return JHipsterModuleResource
      .builder()
      .slug(CHECKSTYLE)
      .withoutProperties()
      .apiDoc("Java", "Add Checkstyle configuration to check unused imports")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "checkstyle")
      .factory(checkstyle::buildModule);
  }
}
