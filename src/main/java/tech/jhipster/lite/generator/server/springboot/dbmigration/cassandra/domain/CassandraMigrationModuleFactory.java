package tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.*;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class CassandraMigrationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/dbmigration/cassandra");
  private static final String DOCKER_COMPOSE_COMMAND = "docker compose -f src/main/docker/cassandra-migration.yml up -d";
  private static final String CASSANDRA = "cassandra";
  private final DockerImages dockerImages;

  public CassandraMigrationModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("cassandraDockerImage", dockerImages.get(CASSANDRA).fullName())
        .and()
      .documentation(documentationTitle("Cassandra Migration"), SOURCE.file("cassandra-migration.md"))
      .startupCommand(DOCKER_COMPOSE_COMMAND)
      .files()
        .add(SOURCE.template("Cassandra-Migration.Dockerfile"), toSrcMainDocker().append(CASSANDRA).append("Cassandra-Migration.Dockerfile"))
        .add(SOURCE.file("cassandra-migration.yml"), toSrcMainDocker().append("cassandra-migration.yml"))
        .add(SOURCE.file("autoMigrate.sh"), toSrcMainDockerScripts().append("autoMigrate.sh"))
        .add(SOURCE.file("execute-cql.sh"), toSrcMainDockerScripts().append("execute-cql.sh"))
        .add(SOURCE.file("create-migration-keyspace.cql"), toSrcMainResourcesCql().append("create-migration-keyspace.cql"))
        .add(SOURCE.file("README.md"), toSrcMainResourcesCql().append("changelog").append("README.md"))
        .and()
      .build();
    //@formatter:on
  }

  private JHipsterDestination toSrcMainResourcesCql() {
    return toSrcMainResources().append("config").append("cql");
  }

  private JHipsterDestination toSrcMainDockerScripts() {
    return toSrcMainDocker().append(CASSANDRA).append("scripts");
  }
}
