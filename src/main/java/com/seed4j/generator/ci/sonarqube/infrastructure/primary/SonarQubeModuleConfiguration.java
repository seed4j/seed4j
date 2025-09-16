package com.seed4j.generator.ci.sonarqube.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CODE_COVERAGE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SONARQUBE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SONARQUBE_JAVA_BACKEND;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SONARQUBE_JAVA_BACKEND_AND_FRONTEND;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SONARQUBE_TYPESCRIPT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.seed4j.generator.ci.sonarqube.application.SonarQubeApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SonarQubeModuleConfiguration {

  @Bean
  Seed4JModuleResource sonarqubeBackendModule(SonarQubeApplicationService sonarQube) {
    return Seed4JModuleResource.builder()
      .slug(SONARQUBE_JAVA_BACKEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("SonarQube", "Add Sonar configuration for Java Backend to inspect code quality")
      .organization(
        Seed4JModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
      )
      .tags("server", "sonar", "sonarqube")
      .factory(sonarQube::buildBackendModule);
  }

  @Bean
  Seed4JModuleResource sonarqubeBackendFrontendModule(SonarQubeApplicationService sonarQube) {
    return Seed4JModuleResource.builder()
      .slug(SONARQUBE_JAVA_BACKEND_AND_FRONTEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("SonarQube", "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
      .organization(
        Seed4JModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
      )
      .tags("server", "frontend", "sonar", "sonarqube")
      .factory(sonarQube::buildBackendFrontendModule);
  }

  @Bean
  Seed4JModuleResource sonarqubeTypescriptModule(SonarQubeApplicationService sonarTypescript) {
    return Seed4JModuleResource.builder()
      .slug(SONARQUBE_TYPESCRIPT)
      .withoutProperties()
      .apiDoc("Typescript", "Add Sonar to project")
      .organization(Seed4JModuleOrganization.builder().feature(SONARQUBE).addDependency(TYPESCRIPT).build())
      .tags("typescript")
      .factory(sonarTypescript::buildTypescriptModule);
  }

  private Seed4JModulePropertiesDefinition propertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder().addProjectName().addProjectBaseName().addIndentation().build();
  }
}
