package com.seed4j.generator.buildtool.maven.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL_WRAPPER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MAVEN_WRAPPER;

import com.seed4j.generator.buildtool.maven.application.MavenApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MavenModuleConfiguration {

  @Bean
  SeedModuleResource mavenModule(MavenApplicationService maven) {
    return SeedModuleResource.builder()
      .slug(MAVEN_JAVA)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc("Build Tool", "Init Maven project with pom.xml")
      .organization(SeedModuleOrganization.builder().feature(JAVA_BUILD_TOOL).addDependency(INIT).build())
      .tags("buildtool", "test")
      .factory(maven::buildMavenModule);
  }

  @Bean
  SeedModuleResource mavenWrapperModule(MavenApplicationService maven) {
    return SeedModuleResource.builder()
      .slug(MAVEN_WRAPPER)
      .withoutProperties()
      .apiDoc("Build Tool", "Add maven wrapper")
      .organization(SeedModuleOrganization.builder().feature(JAVA_BUILD_TOOL_WRAPPER).addDependency(MAVEN_JAVA).build())
      .tags("buildtool", "test")
      .factory(maven::buildMavenWrapperModule);
  }
}
