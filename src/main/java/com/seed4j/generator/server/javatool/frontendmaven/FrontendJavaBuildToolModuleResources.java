package com.seed4j.generator.server.javatool.frontendmaven;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.FRONTEND_JAVA_BUILD_TOOL_PLUGIN;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CYPRESS_COMPONENT_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FRONTEND_MAVEN_PLUGIN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FRONTEND_MAVEN_PLUGIN_CACHE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.FRONTEND_MAVEN_PLUGIN_MERGE_COVERAGE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.NODE_GRADLE_PLUGIN;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class FrontendJavaBuildToolModuleResources {

  private static final String[] TAGS = { "server", "tools" };
  private static final String FRONTEND_JAVA_PLUGIN = "Frontend Java Plugin";

  private FrontendJavaBuildToolModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class FrontendMavenPlugin implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FRONTEND_MAVEN_PLUGIN)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().addNodePackageManager().build()
        )
        .apiDoc(FRONTEND_JAVA_PLUGIN, "Add Frontend Maven Plugin")
        .organization(
          Seed4JModuleOrganization.builder()
            .feature(FRONTEND_JAVA_BUILD_TOOL_PLUGIN)
            .addDependency(SPRING_SERVER)
            .addDependency(SPRING_MVC_SERVER)
            .addDependency(CLIENT_CORE)
            .addDependency(MAVEN_JAVA)
            .build()
        )
        .tags(TAGS)
        .factory(new FrontendJavaBuildToolModuleFactory(services)::buildFrontendMavenModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class FrontendMavenPluginCache implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FRONTEND_MAVEN_PLUGIN_CACHE)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addNodePackageManager().build())
        .apiDoc(
          FRONTEND_JAVA_PLUGIN,
          "Add cache - by computing resources checksum - to avoid rebuilding frontend on successive maven builds"
        )
        .organization(Seed4JModuleOrganization.builder().addDependency(FRONTEND_MAVEN_PLUGIN).build())
        .tags(TAGS)
        .factory(new FrontendJavaBuildToolModuleFactory(services)::buildFrontendMavenCacheModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class NodeGradlePlugin implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(NODE_GRADLE_PLUGIN)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
        .apiDoc(FRONTEND_JAVA_PLUGIN, "Add node-gradle plugin for building frontend with Gradle")
        .organization(
          Seed4JModuleOrganization.builder()
            .feature(FRONTEND_JAVA_BUILD_TOOL_PLUGIN)
            .addDependency(SPRING_SERVER)
            .addDependency(SPRING_MVC_SERVER)
            .addDependency(CLIENT_CORE)
            .addDependency(GRADLE_JAVA)
            .build()
        )
        .tags(TAGS)
        .factory(new FrontendJavaBuildToolModuleFactory(services)::buildFrontendGradleModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class FrontendMavenPluginMergeCoverage implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(FRONTEND_MAVEN_PLUGIN_MERGE_COVERAGE)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().addNodePackageManager().build()
        )
        .apiDoc(FRONTEND_JAVA_PLUGIN, "Merge Cypress and vitest code coverage")
        .organization(
          Seed4JModuleOrganization.builder()
            .feature(FRONTEND_JAVA_BUILD_TOOL_PLUGIN)
            .addDependency(CYPRESS_COMPONENT_TESTS)
            .addDependency(CLIENT_CORE)
            .addDependency(SPRING_SERVER)
            .addDependency(SPRING_MVC_SERVER)
            .addDependency(MAVEN_JAVA)
            .build()
        )
        .tags("cypress", "vitest", "coverage")
        .factory(new FrontendJavaBuildToolModuleFactory(services)::buildMergeCypressCoverageModule);
    }
  }
}
