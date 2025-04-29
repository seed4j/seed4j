package tech.jhipster.lite.generator.server.sonarqube.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.CODE_COVERAGE_JAVA;
import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.SONAR_QUBE_JAVA;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SONAR_QUBE_JAVA_BACKEND;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SONAR_QUBE_JAVA_BACKEND_AND_FRONTEND;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.sonarqube.application.SonarQubeApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SonarQubeModuleConfiguration {

  @Bean
  JHipsterModuleResource sonarBackendModule(SonarQubeApplicationService sonarQube) {
    return JHipsterModuleResource.builder()
      .slug(SONAR_QUBE_JAVA_BACKEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Sonar", "Add Sonar configuration for Java Backend to inspect code quality")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(SONAR_QUBE_JAVA)
          .addDependency(JAVA_BUILD_TOOL)
          .addDependency(CODE_COVERAGE_JAVA)
          .build()
      )
      .tags("server", "sonar")
      .factory(sonarQube::buildBackendModule);
  }

  @Bean
  JHipsterModuleResource sonarBackendFrontendModule(SonarQubeApplicationService sonarQube) {
    return JHipsterModuleResource.builder()
      .slug(SONAR_QUBE_JAVA_BACKEND_AND_FRONTEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Sonar", "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
      .organization(
        JHipsterModuleOrganization.builder()
          .feature(SONAR_QUBE_JAVA)
          .addDependency(JAVA_BUILD_TOOL)
          .addDependency(CODE_COVERAGE_JAVA)
          .build()
      )
      .tags("server", "sonar")
      .factory(sonarQube::buildBackendFrontendModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addProjectName().addProjectBaseName().addIndentation().build();
  }
}
