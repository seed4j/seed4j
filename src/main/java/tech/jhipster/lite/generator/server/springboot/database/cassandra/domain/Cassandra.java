package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import java.util.List;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class Cassandra {

  public static final String DOCKER_IMAGE_NAME = "cassandra:3.11.12";

  public static Dependency cassandraConnectorJava() {
    return Dependency.builder().groupId("mysql").artifactId("mysql-connector-java").build();
  }

  public static List<String> getScripts() {
    return List.of("autoMigrate", "execute-cql");
  }

  public static List<String> getYmlFiles() {
    return List.of("app", "cassandra-migration");
  }

  public static String getDockerImageName() {
    return DOCKER_IMAGE_NAME;
  }
}
