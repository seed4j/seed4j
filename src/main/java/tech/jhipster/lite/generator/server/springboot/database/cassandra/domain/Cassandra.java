package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class Cassandra {

  public static Dependency cassandraConnectorJava() {
    return Dependency.builder().groupId("mysql").artifactId("mysql-connector-java").build();
  }
}
