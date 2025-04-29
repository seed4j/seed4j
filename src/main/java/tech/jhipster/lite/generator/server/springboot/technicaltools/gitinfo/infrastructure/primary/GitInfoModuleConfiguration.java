package tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.GIT_INFORMATION;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.application.GitInfoApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

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
