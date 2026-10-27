package com.seed4j.generator.buildtool.maven;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_WRAPPER;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class MavenModuleResources {

  private MavenModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Java implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(MAVEN_JAVA)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
        .apiDoc("Build Tool", "Init Maven project with pom.xml")
        .organization(Seed4JModuleOrganization.builder().feature(JAVA_BUILD_TOOL).addDependency(INIT).build())
        .tags("buildtool", "test")
        .factory(new MavenModuleFactory()::buildMavenModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Wrapper implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(MAVEN_WRAPPER)
        .withoutProperties()
        .apiDoc("Build Tool", "Add maven wrapper")
        .organization(Seed4JModuleOrganization.builder().feature(JAVA_BUILD_TOOL_WRAPPER).addDependency(MAVEN_JAVA).build())
        .tags("buildtool", "test")
        .factory(new MavenModuleFactory()::buildMavenWrapperModule);
    }
  }
}
