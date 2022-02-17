package tech.jhipster.lite.generator.server.springboot.docker.domain;

public class SpringBootDocker {

  private static final String DOCKER_BASE_IMAGE = "eclipse-temurin:17-jre-focal";
  private static final String DOCKER_PLATFORM_ARCHITECTURE = "amd64";

  private SpringBootDocker() {}

  public static String getDockerBaseImage() {
    return DOCKER_BASE_IMAGE;
  }

  public static String getDockerPlatformArchitecture() {
    return DOCKER_PLATFORM_ARCHITECTURE;
  }
}
