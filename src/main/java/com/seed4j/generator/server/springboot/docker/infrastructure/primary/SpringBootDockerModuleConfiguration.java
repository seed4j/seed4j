package com.seed4j.generator.server.springboot.docker.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DOCKERFILE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DOCKERFILE_GRADLE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DOCKERFILE_MAVEN;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JIB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_DOCKER_COMPOSE;

import com.seed4j.generator.server.springboot.docker.application.SpringBootDockerApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootDockerModuleConfiguration {

  private static final String SPRING_BOOT_TOOLS_GROUP = "Spring Boot - Tools";
  private static final String SERVER_TAG = "server";
  private static final String SPRING_TAG = "spring";
  private static final String DOCKER_TAG = "docker";
  private static final String JIB_TAG = "jib";
  private static final String SPRING_BOOT_TAG = "spring-boot";

  @Bean
  Seed4JModuleResource jibModule(SpringBootDockerApplicationService springBootDocker) {
    return Seed4JModuleResource.builder()
      .slug(JIB)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Docker image building with Jib")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, JIB_TAG)
      .factory(springBootDocker::buildJibModule);
  }

  @Bean
  Seed4JModuleResource dockerFileMavenModule(SpringBootDockerApplicationService springBootDocker) {
    return Seed4JModuleResource.builder()
      .slug(DOCKERFILE_MAVEN)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Dockerfile with maven commands")
      .organization(Seed4JModuleOrganization.builder().feature(DOCKERFILE).addDependency(MAVEN_WRAPPER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildDockerFileMavenModule);
  }

  @Bean
  Seed4JModuleResource dockerFileGradleModule(SpringBootDockerApplicationService springBootDocker) {
    return Seed4JModuleResource.builder()
      .slug(DOCKERFILE_GRADLE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Dockerfile with gradle commands")
      .organization(Seed4JModuleOrganization.builder().feature(DOCKERFILE).addDependency(GRADLE_WRAPPER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildDockerFileGradleModule);
  }

  @Bean
  Seed4JModuleResource springBootDockerComposeIntegrationModule(SpringBootDockerApplicationService springBootDocker) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_DOCKER_COMPOSE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Configure Spring Boot and docker compose integration, to make local development easier")
      .organization(Seed4JModuleOrganization.builder().addDependency(MAVEN_JAVA).addDependency(SPRING_BOOT).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildSpringBootDockerComposeModule);
  }
}
