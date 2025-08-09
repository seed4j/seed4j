package com.seed4j.generator.server.springboot.docker.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.DOCKERFILE;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DOCKERFILE_GRADLE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.DOCKERFILE_MAVEN;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.JIB;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MAVEN_WRAPPER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_DOCKER_COMPOSE;

import com.seed4j.generator.server.springboot.docker.application.SpringBootDockerApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
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
  SeedModuleResource jibModule(SpringBootDockerApplicationService springBootDocker) {
    return SeedModuleResource.builder()
      .slug(JIB)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Docker image building with Jib")
      .organization(SeedModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, JIB_TAG)
      .factory(springBootDocker::buildJibModule);
  }

  @Bean
  SeedModuleResource dockerFileMavenModule(SpringBootDockerApplicationService springBootDocker) {
    return SeedModuleResource.builder()
      .slug(DOCKERFILE_MAVEN)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Dockerfile with maven commands")
      .organization(SeedModuleOrganization.builder().feature(DOCKERFILE).addDependency(MAVEN_WRAPPER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildDockerFileMavenModule);
  }

  @Bean
  SeedModuleResource dockerFileGradleModule(SpringBootDockerApplicationService springBootDocker) {
    return SeedModuleResource.builder()
      .slug(DOCKERFILE_GRADLE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Dockerfile with gradle commands")
      .organization(SeedModuleOrganization.builder().feature(DOCKERFILE).addDependency(GRADLE_WRAPPER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildDockerFileGradleModule);
  }

  @Bean
  SeedModuleResource springBootDockerComposeIntegrationModule(SpringBootDockerApplicationService springBootDocker) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_DOCKER_COMPOSE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Configure Spring Boot and docker compose integration, to make local development easier")
      .organization(SeedModuleOrganization.builder().addDependency(MAVEN_JAVA).addDependency(SPRING_BOOT).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildSpringBootDockerComposeModule);
  }
}
