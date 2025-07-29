package com.seed4j.generator.buildtool.gradle.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL_WRAPPER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INIT;

import com.seed4j.generator.buildtool.gradle.application.GradleApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GradleModuleConfiguration {

  @Bean
  JHipsterModuleResource gradleModule(GradleApplicationService gradle) {
    return JHipsterModuleResource.builder()
      .slug(GRADLE_JAVA)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc("Build Tool", "Init Gradle project with kotlin DSL")
      .organization(JHipsterModuleOrganization.builder().feature(JAVA_BUILD_TOOL).addDependency(INIT).build())
      .tags("buildtool", "test")
      .factory(gradle::buildGradleModule);
  }

  @Bean
  JHipsterModuleResource gradleWrapperModule(GradleApplicationService gradle) {
    return JHipsterModuleResource.builder()
      .slug(GRADLE_WRAPPER)
      .withoutProperties()
      .apiDoc("Build Tool", "Add gradle wrapper")
      .organization(JHipsterModuleOrganization.builder().feature(JAVA_BUILD_TOOL_WRAPPER).addDependency(GRADLE_JAVA).build())
      .tags("buildtool", "test")
      .factory(gradle::buildGradleWrapperModule);
  }
}
