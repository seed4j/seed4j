package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class EurekaClient {

  public static final String JHIPSTER_REGISTRY_DOCKER_IMAGE = "jhipster/jhipster-registry:v7.1.0";
  public static final String SPRINGFRAMEWORK_CLOUD_GROUP_ID = "org.springframework.cloud";

  private EurekaClient() {
    // Cannot be instantiated
  }

  public static Dependency springCloudDependencyManagement() {
    return Dependency
      .builder()
      .groupId(SPRINGFRAMEWORK_CLOUD_GROUP_ID)
      .artifactId("spring-cloud-dependencies")
      .version("\\${spring-cloud.version}")
      .type("pom")
      .scope("import")
      .build();
  }

  public static Dependency springCloudStarterBootstrap() {
    return Dependency.builder().groupId(SPRINGFRAMEWORK_CLOUD_GROUP_ID).artifactId("spring-cloud-starter-bootstrap").build();
  }

  public static Dependency springCloudNetflixEurekaClient() {
    return Dependency
      .builder()
      .groupId(SPRINGFRAMEWORK_CLOUD_GROUP_ID)
      .artifactId("spring-cloud-starter-netflix-eureka-client")
      .version("\\${spring-cloud-netflix-eureka-client.version}")
      .build();
  }
}
