package com.seed4j.generator.server.javatool.frontendmaven.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.FRONTEND_JAVA_BUILD_TOOL_PLUGIN;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FRONTEND_MAVEN_PLUGIN;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FRONTEND_MAVEN_PLUGIN_CACHE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.FRONTEND_MAVEN_PLUGIN_MERGE_COVERAGE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.NODE_GRADLE_PLUGIN;

import com.seed4j.generator.server.javatool.frontendmaven.application.FrontendJavaBuildToolApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FrontendJavaBuildToolModuleConfiguration {

  private static final String[] TAGS = { "server", "tools" };
  private static final String FRONTEND_JAVA_PLUGIN = "Frontend Java Plugin";

  @Bean
  SeedModuleResource frontendMavenModule(FrontendJavaBuildToolApplicationService frontendJavaBuildTool) {
    return SeedModuleResource.builder()
      .slug(FRONTEND_MAVEN_PLUGIN)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().addNodePackageManager().build()
      )
      .apiDoc(FRONTEND_JAVA_PLUGIN, "Add Frontend Maven Plugin")
      .organization(
        SeedModuleOrganization.builder()
          .feature(FRONTEND_JAVA_BUILD_TOOL_PLUGIN)
          .addDependency(SPRING_SERVER)
          .addDependency(SPRING_MVC_SERVER)
          .addDependency(CLIENT_CORE)
          .addDependency(MAVEN_JAVA)
          .build()
      )
      .tags(TAGS)
      .factory(frontendJavaBuildTool::buildFrontendMavenModule);
  }

  @Bean
  SeedModuleResource frontendMavenCacheModule(FrontendJavaBuildToolApplicationService frontendJavaBuildTool) {
    return SeedModuleResource.builder()
      .slug(FRONTEND_MAVEN_PLUGIN_CACHE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addNodePackageManager().build())
      .apiDoc(FRONTEND_JAVA_PLUGIN, "Add cache - by computing resources checksum - to avoid rebuilding frontend on successive maven builds")
      .organization(SeedModuleOrganization.builder().addDependency(FRONTEND_MAVEN_PLUGIN).build())
      .tags(TAGS)
      .factory(frontendJavaBuildTool::buildFrontendMavenCacheModule);
  }

  @Bean
  SeedModuleResource frontendGradleModule(FrontendJavaBuildToolApplicationService frontendJavaBuildTool) {
    return SeedModuleResource.builder()
      .slug(NODE_GRADLE_PLUGIN)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(FRONTEND_JAVA_PLUGIN, "Add node-gradle plugin for building frontend with Gradle")
      .organization(
        SeedModuleOrganization.builder()
          .feature(FRONTEND_JAVA_BUILD_TOOL_PLUGIN)
          .addDependency(SPRING_SERVER)
          .addDependency(SPRING_MVC_SERVER)
          .addDependency(CLIENT_CORE)
          .addDependency(GRADLE_JAVA)
          .build()
      )
      .tags(TAGS)
      .factory(frontendJavaBuildTool::buildFrontendGradleModule);
  }

  @Bean
  SeedModuleResource mergeCypressMergeCoverageModule(FrontendJavaBuildToolApplicationService frontendJavaBuildTool) {
    return SeedModuleResource.builder()
      .slug(FRONTEND_MAVEN_PLUGIN_MERGE_COVERAGE)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().addNodePackageManager().build()
      )
      .apiDoc(FRONTEND_JAVA_PLUGIN, "Merge Cypress and vitest code coverage")
      .organization(
        SeedModuleOrganization.builder()
          .feature(FRONTEND_JAVA_BUILD_TOOL_PLUGIN)
          .addDependency(CYPRESS_COMPONENT_TESTS)
          .addDependency(CLIENT_CORE)
          .addDependency(SPRING_SERVER)
          .addDependency(SPRING_MVC_SERVER)
          .addDependency(MAVEN_JAVA)
          .build()
      )
      .tags("cypress", "vitest", "coverage")
      .factory(frontendJavaBuildTool::buildMergeCypressCoverageModule);
  }
}
