package com.seed4j.generator.server.springboot.technicaltools.gitinfo.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GIT_INFORMATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.technicaltools.gitinfo.application.GitInfoApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitInfoModuleConfiguration {

  @Bean
  Seed4JModuleResource gitInfoModule(GitInfoApplicationService gitInfo) {
    return Seed4JModuleResource.builder()
      .slug(GIT_INFORMATION)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Tools", "Injecting Git Information into Spring")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "git", "git-information")
      .factory(gitInfo::buildModule);
  }
}
