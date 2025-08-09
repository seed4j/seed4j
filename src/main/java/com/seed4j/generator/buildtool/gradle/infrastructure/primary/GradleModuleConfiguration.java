package com.seed4j.generator.buildtool.gradle.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JAVA_BUILD_TOOL_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.INIT;

import com.seed4j.generator.buildtool.gradle.application.GradleApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GradleModuleConfiguration {

  @Bean
  SeedModuleResource gradleModule(GradleApplicationService gradle) {
    return SeedModuleResource.builder()
      .slug(GRADLE_JAVA)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc("Build Tool", "Init Gradle project with kotlin DSL")
      .organization(SeedModuleOrganization.builder().feature(JAVA_BUILD_TOOL).addDependency(INIT).build())
      .tags("buildtool", "test")
      .factory(gradle::buildGradleModule);
  }

  @Bean
  SeedModuleResource gradleWrapperModule(GradleApplicationService gradle) {
    return SeedModuleResource.builder()
      .slug(GRADLE_WRAPPER)
      .withoutProperties()
      .apiDoc("Build Tool", "Add gradle wrapper")
      .organization(SeedModuleOrganization.builder().feature(JAVA_BUILD_TOOL_WRAPPER).addDependency(GRADLE_JAVA).build())
      .tags("buildtool", "test")
      .factory(gradle::buildGradleWrapperModule);
  }
}
