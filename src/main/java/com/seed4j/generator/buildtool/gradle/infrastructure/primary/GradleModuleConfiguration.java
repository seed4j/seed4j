package com.seed4j.generator.buildtool.gradle.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;

import com.seed4j.generator.buildtool.gradle.application.GradleApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GradleModuleConfiguration {

  @Bean
  Seed4JModuleResource gradleModule(GradleApplicationService gradle) {
    return Seed4JModuleResource.builder()
      .slug(GRADLE_JAVA)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc("Build Tool", "Init Gradle project with kotlin DSL")
      .organization(Seed4JModuleOrganization.builder().feature(JAVA_BUILD_TOOL).addDependency(INIT).build())
      .tags("buildtool", "test")
      .factory(gradle::buildGradleModule);
  }

  @Bean
  Seed4JModuleResource gradleWrapperModule(GradleApplicationService gradle) {
    return Seed4JModuleResource.builder()
      .slug(GRADLE_WRAPPER)
      .withoutProperties()
      .apiDoc("Build Tool", "Add gradle wrapper")
      .organization(Seed4JModuleOrganization.builder().feature(JAVA_BUILD_TOOL_WRAPPER).addDependency(GRADLE_JAVA).build())
      .tags("buildtool", "test")
      .factory(gradle::buildGradleWrapperModule);
  }
}
