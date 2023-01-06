package tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.*;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.RegexNeedleAfterReplacer;

public class CassandraMigrationModuleFactory {

  private static final RegexNeedleAfterReplacer CASSANDRA_MANAGER_NEEDLE = lineAfterRegex("createTestKeyspace\\(session\\);");
  private static final String LOAD_MIGRATION_SCRIPTS = "TestCassandraMigrationLoader.loadMigrationScripts(session);";
  private static final JHipsterSource SOURCE = from("server/springboot/dbmigration/cassandra");
  private static final String DOCKER_COMPOSE_COMMAND = "docker compose -f src/main/docker/cassandra-migration.yml up -d";
  private static final String CASSANDRA = "cassandra";
  private final DockerImages dockerImages;

  public CassandraMigrationModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Indentation indentation = properties.indentation();

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(cassandraUnitDependency())
        .and()
      .context()
        .put("cassandraDockerImage", dockerImages.get(CASSANDRA).fullName())
        .and()
      .documentation(documentationTitle("Cassandra Migration"), SOURCE.file("cassandra-migration.md"))
      .startupCommand(DOCKER_COMPOSE_COMMAND)
      .files()
        .add(SOURCE.template("TestCassandraMigrationLoader.java"), toSrcTestJava().append(packagePath).append("TestCassandraMigrationLoader.java"))
        .add(SOURCE.template("Cassandra-Migration.Dockerfile"), toSrcMainDocker().append(CASSANDRA).append("Cassandra-Migration.Dockerfile"))
        .add(SOURCE.file("cassandra-migration.yml"), toSrcMainDocker().append("cassandra-migration.yml"))
        .add(SOURCE.file("autoMigrate.sh"), toSrcMainDockerScripts().append("autoMigrate.sh"))
        .add(SOURCE.file("execute-cql.sh"), toSrcMainDockerScripts().append("execute-cql.sh"))
        .add(SOURCE.file("create-migration-keyspace.cql"), toSrcMainResourcesCql().append("create-migration-keyspace.cql"))
        .add(SOURCE.file("README.md"), toSrcMainResourcesCql().append("changelog").append("README.md"))
        .and()
      .mandatoryReplacements()
        .in(path("src/test/java/" + packagePath + "/TestCassandraManager.java"))
          .add(CASSANDRA_MANAGER_NEEDLE, LOAD_MIGRATION_SCRIPTS.indent(3 * indentation.spacesCount()))
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private JavaDependency cassandraUnitDependency() {
    return javaDependency()
      .groupId("org.cassandraunit")
      .artifactId("cassandra-unit")
      .versionSlug("cassandraunit")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JHipsterDestination toSrcMainResourcesCql() {
    return toSrcMainResources().append("config").append("cql");
  }

  private JHipsterDestination toSrcMainDockerScripts() {
    return toSrcMainDocker().append(CASSANDRA).append("scripts");
  }
}
