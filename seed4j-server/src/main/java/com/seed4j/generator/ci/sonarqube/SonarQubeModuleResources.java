package com.seed4j.generator.ci.sonarqube;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CODE_COVERAGE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SONARQUBE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SONARQUBE_JAVA_BACKEND;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SONARQUBE_JAVA_BACKEND_AND_FRONTEND;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SONARQUBE_TYPESCRIPT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class SonarQubeModuleResources {

  private SonarQubeModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class JavaBackend implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SONARQUBE_JAVA_BACKEND)
        .propertiesDefinition(propertiesDefinition())
        .apiDoc("SonarQube", "Add Sonar configuration for Java Backend to inspect code quality")
        .organization(
          Seed4JModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
        )
        .tags("server", "sonar", "sonarqube")
        .factory(new SonarQubeModuleFactory(services)::buildBackendModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class JavaBackendAndFrontend implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SONARQUBE_JAVA_BACKEND_AND_FRONTEND)
        .propertiesDefinition(propertiesDefinition())
        .apiDoc("SonarQube", "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
        .organization(
          Seed4JModuleOrganization.builder().feature(SONARQUBE).addDependency(JAVA_BUILD_TOOL).addDependency(CODE_COVERAGE_JAVA).build()
        )
        .tags("server", "frontend", "sonar", "sonarqube")
        .factory(new SonarQubeModuleFactory(services)::buildBackendFrontendModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Typescript implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SONARQUBE_TYPESCRIPT)
        .withoutProperties()
        .apiDoc("Typescript", "Add Sonar to project")
        .organization(Seed4JModuleOrganization.builder().feature(SONARQUBE).addDependency(TYPESCRIPT).build())
        .tags("typescript")
        .factory(new SonarQubeModuleFactory(services)::buildTypescriptModule);
    }
  }

  private static Seed4JModulePropertiesDefinition propertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder().addProjectName().addProjectBaseName().addIndentation().build();
  }
}
