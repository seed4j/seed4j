package tech.jhipster.lite.generator.server.sonar.domain;

public class Sonar {

  public static final String SONARQUBE_DOCKER_IMAGE = "sonarqube:9.2.4-community";

  private Sonar() {}

  public static String getSonarqubeDockerImage() {
    return SONARQUBE_DOCKER_IMAGE;
  }
}
