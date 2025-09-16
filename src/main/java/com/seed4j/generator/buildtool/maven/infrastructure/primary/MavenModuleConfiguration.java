package com.seed4j.generator.buildtool.maven.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_WRAPPER;

import com.seed4j.generator.buildtool.maven.application.MavenApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MavenModuleConfiguration {

  @Bean
  Seed4JModuleResource mavenModule(MavenApplicationService maven) {
    return Seed4JModuleResource.builder()
      .slug(MAVEN_JAVA)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc("Build Tool", "Init Maven project with pom.xml")
      .organization(Seed4JModuleOrganization.builder().feature(JAVA_BUILD_TOOL).addDependency(INIT).build())
      .tags("buildtool", "test")
      .factory(maven::buildMavenModule);
  }

  @Bean
  Seed4JModuleResource mavenWrapperModule(MavenApplicationService maven) {
    return Seed4JModuleResource.builder()
      .slug(MAVEN_WRAPPER)
      .withoutProperties()
      .apiDoc("Build Tool", "Add maven wrapper")
      .organization(Seed4JModuleOrganization.builder().feature(JAVA_BUILD_TOOL_WRAPPER).addDependency(MAVEN_JAVA).build())
      .tags("buildtool", "test")
      .factory(maven::buildMavenWrapperModule);
  }
}
