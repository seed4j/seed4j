package com.seed4j.generator.ci.sonarqube.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CODE_COVERAGE_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SONARQUBE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SONARQUBE_JAVA_BACKEND;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SONARQUBE_JAVA_BACKEND_AND_FRONTEND;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SONARQUBE_TYPESCRIPT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.TYPESCRIPT;

import com.seed4j.generator.ci.sonarqube.application.SonarQubeApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SonarQubeModuleConfiguration {

  @Bean
  SeedModuleResource sonarqubeBackendModule(SonarQubeApplicationService sonarQube) {
    return SeedModuleResource.builder()
      .slug(SONARQUBE_JAVA_BACKEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("SonarQube", "Add Sonar configuration for Java Backend to inspect code quality")
      .organization(
        SeedModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
      )
      .tags("server", "sonar", "sonarqube")
      .factory(sonarQube::buildBackendModule);
  }

  @Bean
  SeedModuleResource sonarqubeBackendFrontendModule(SonarQubeApplicationService sonarQube) {
    return SeedModuleResource.builder()
      .slug(SONARQUBE_JAVA_BACKEND_AND_FRONTEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("SonarQube", "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
      .organization(
        SeedModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
      )
      .tags("server", "frontend", "sonar", "sonarqube")
      .factory(sonarQube::buildBackendFrontendModule);
  }

  @Bean
  SeedModuleResource sonarqubeTypescriptModule(SonarQubeApplicationService sonarTypescript) {
    return SeedModuleResource.builder()
      .slug(SONARQUBE_TYPESCRIPT)
      .withoutProperties()
      .apiDoc("Typescript", "Add Sonar to project")
      .organization(SeedModuleOrganization.builder().feature(SONARQUBE).addDependency(TYPESCRIPT).build())
      .tags("typescript")
      .factory(sonarTypescript::buildTypescriptModule);
  }

  private SeedModulePropertiesDefinition propertiesDefinition() {
    return SeedModulePropertiesDefinition.builder().addProjectName().addProjectBaseName().addIndentation().build();
  }
}
