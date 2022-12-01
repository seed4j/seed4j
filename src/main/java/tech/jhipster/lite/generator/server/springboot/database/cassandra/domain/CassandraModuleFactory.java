package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class CassandraModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/database/cassandra");
  private static final String DOCKER_COMPOSE_COMMAND = "docker compose -f src/main/docker/cassandra.yml up -d";
  private final DockerImages dockerImages;

  public CassandraModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-cassandra"))
      .and()
      .documentation(documentationTitle("Cassandra"), SOURCE.template("cassandra.md"))
      .startupCommand(startupCommand())
      .context()
        .put("cassandraDockerImage", dockerImages.get("cassandra").fullName())
        .and()
      .files()
        .add(SOURCE.template("cassandra.yml.mustache"), toSrcMainDocker().append("cassandra.yml"))
        .add(SOURCE.template("keyspace.cql.mustache"), toSrcMainDocker().append("cassandra-init").append("keyspace.cql"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.data.cassandra.keyspace-name"), propertyValue(properties.projectBaseName().get()))
        .set(propertyKey("spring.data.cassandra.contact-points"), propertyValue("127.0.0.1"))
        .set(propertyKey("spring.data.cassandra.port"), propertyValue("9042"))
        .set(propertyKey("spring.data.cassandra.local-datacenter"), propertyValue("datacenter1"))
        .set(propertyKey("spring.data.cassandra.schema-action"), propertyValue("CREATE_IF_NOT_EXISTS"))
        .and()
      .build();
    //@formatter:on
  }

  private String startupCommand() {
    return DOCKER_COMPOSE_COMMAND;
  }
}
