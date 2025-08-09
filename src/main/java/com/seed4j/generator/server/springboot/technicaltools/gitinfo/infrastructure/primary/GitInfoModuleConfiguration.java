package com.seed4j.generator.server.springboot.technicaltools.gitinfo.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.GIT_INFORMATION;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.technicaltools.gitinfo.application.GitInfoApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GitInfoModuleConfiguration {

  @Bean
  SeedModuleResource gitInfoModule(GitInfoApplicationService gitInfo) {
    return SeedModuleResource.builder()
      .slug(GIT_INFORMATION)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Tools", "Injecting Git Information into Spring")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "git", "git-information")
      .factory(gitInfo::buildModule);
  }
}
