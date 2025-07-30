package com.seed4j.generator.server.springboot.technicaltools.gitinfo.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GIT_INFORMATION;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.technicaltools.gitinfo.application.GitInfoApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitInfoModuleConfiguration {

  @Bean
  JHipsterModuleResource gitInfoModule(GitInfoApplicationService gitInfo) {
    return JHipsterModuleResource.builder()
      .slug(GIT_INFORMATION)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Tools", "Injecting Git Information into Spring")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "git", "git-information")
      .factory(gitInfo::buildModule);
  }
}
