package com.seed4j.generator.server.springboot.docker.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.DOCKERFILE;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DOCKERFILE_GRADLE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.DOCKERFILE_MAVEN;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.GRADLE_WRAPPER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JIB;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MAVEN_WRAPPER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_DOCKER_COMPOSE;

import com.seed4j.generator.server.springboot.docker.application.SpringBootDockerApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
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
  JHipsterModuleResource jibModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource.builder()
      .slug(JIB)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Docker image building with Jib")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, JIB_TAG)
      .factory(springBootDocker::buildJibModule);
  }

  @Bean
  JHipsterModuleResource dockerFileMavenModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource.builder()
      .slug(DOCKERFILE_MAVEN)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Dockerfile with maven commands")
      .organization(JHipsterModuleOrganization.builder().feature(DOCKERFILE).addDependency(MAVEN_WRAPPER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildDockerFileMavenModule);
  }

  @Bean
  JHipsterModuleResource dockerFileGradleModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource.builder()
      .slug(DOCKERFILE_GRADLE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Add Dockerfile with gradle commands")
      .organization(JHipsterModuleOrganization.builder().feature(DOCKERFILE).addDependency(GRADLE_WRAPPER).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildDockerFileGradleModule);
  }

  @Bean
  JHipsterModuleResource springBootDockerComposeIntegrationModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_DOCKER_COMPOSE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(SPRING_BOOT_TOOLS_GROUP, "Configure Spring Boot and docker compose integration, to make local development easier")
      .organization(JHipsterModuleOrganization.builder().addDependency(MAVEN_JAVA).addDependency(SPRING_BOOT).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCKER_TAG)
      .factory(springBootDocker::buildSpringBootDockerComposeModule);
  }
}
