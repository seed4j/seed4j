package tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.always;

import java.util.regex.Pattern;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.module.domain.replacement.RegexReplacer;

public class CassandraMigrationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/dbmigration/cassandra");
  private static final String DOCKER_COMPOSE_COMMAND = "docker compose -f src/main/docker/cassandra-migration.yml up -d";
  private static final String CASSANDRA = "cassandra";
  private static final String CASSANDRA_MIGRATION_APPLICATION_LISTENER = "TestCassandraMigrationLoader";
  private static final Pattern CURRENT_APPLICATION_LISTENERS = Pattern.compile(
    "(org\\.springframework\\.context\\.ApplicationListener=.+)"
  );
  private static final ElementReplacer CURRENT_APPLICATION_LISTENERS_NEEDLE = new RegexReplacer(always(), CURRENT_APPLICATION_LISTENERS);
  private final DockerImages dockerImages;

  public CassandraMigrationModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

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
        .add(SOURCE.file("create-migration-keyspace.cql"), toSrcMainResourcesCql().append("create-migration-keyspace.cql"))
        .add(SOURCE.file("README.md"), toSrcMainResourcesCql().append("changelog").append("README.md"))
        .batch(SOURCE, toSrcMainDockerScripts())
          .addFile("autoMigrate.sh")
          .addFile("execute-cql.sh")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/test/resources/META-INF/spring.factories"))
          .add(CURRENT_APPLICATION_LISTENERS_NEEDLE, concatenatedApplicationListeners(packagePath))
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private String concatenatedApplicationListeners(String packagePath) {
    return "$1" + ", " + toPackageName(packagePath) + "." + CASSANDRA_MIGRATION_APPLICATION_LISTENER;
  }

  private String toPackageName(String packagePath) {
    return packagePath.replace("/", ".");
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
