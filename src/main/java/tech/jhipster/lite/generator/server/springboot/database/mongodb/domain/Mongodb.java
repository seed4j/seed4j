package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class Mongodb {

  public static final String MONGODB_DOCKER_VERSION = "4.4.11";

  public static final String MONGODB_DOCKER_IMAGE = "mongo:" + MONGODB_DOCKER_VERSION;

  private Mongodb() {}

  public static String getMongodbDockerVersion() {
    return MONGODB_DOCKER_VERSION;
  }

  public static String getMongodbDockerImage() {
    return MONGODB_DOCKER_IMAGE;
  }

  public static Dependency mongodbDriver() {
    return Dependency.builder().groupId("org.mongodb").artifactId("mongodb-driver-sync").build();
  }
}
