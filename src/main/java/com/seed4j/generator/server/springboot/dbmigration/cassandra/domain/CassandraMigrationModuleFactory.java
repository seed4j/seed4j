package com.seed4j.generator.server.springboot.dbmigration.cassandra.domain;

import static com.seed4j.module.domain.JHipsterModule.dockerComposeFile;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainDocker;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainResources;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CassandraMigrationModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/dbmigration/cassandra");
  private static final String CASSANDRA = "cassandra";
  private final DockerImages dockerImages;

  public CassandraMigrationModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.basePackage().get() + ".";

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(cassandraUnitDependency())
        .and()
      .context()
        .put("cassandraDockerImage", dockerImages.get(CASSANDRA).fullName())
        .and()
      .documentation(documentationTitle("Cassandra Migration"), SOURCE.file("cassandra-migration.md"))
      .startupCommands()
        .dockerCompose("src/main/docker/cassandra-migration.yml")
        .and()
      .files()
        .add(SOURCE.template("TestCassandraMigrationLoader.java"), toSrcTestJava().append(packagePath).append("TestCassandraMigrationLoader.java"))
        .add(SOURCE.template("Cassandra-Migration.Dockerfile"), toSrcMainDocker().append(CASSANDRA).append("Cassandra-Migration.Dockerfile"))
        .add(SOURCE.file("cassandra-migration.yml"), toSrcMainDocker().append("cassandra-migration.yml"))
        .add(SOURCE.file("cassandra-migration-spring-docker-compose.yml"), toSrcMainDocker().append("cassandra-migration-spring-docker-compose.yml"))
        .add(SOURCE.file("create-migration-keyspace.cql"), toSrcMainResourcesCql().append("create-migration-keyspace.cql"))
        .add(SOURCE.file("README.md"), toSrcMainResourcesCql().append("changelog").append("README.md"))
        .batch(SOURCE, toSrcMainDockerScripts())
          .addFile("autoMigrate.sh")
          .addFile("execute-cql.sh")
          .and()
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/cassandra-migration-spring-docker-compose.yml"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestCassandraMigrationLoader"))
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency cassandraUnitDependency() {
    return javaDependency()
      .groupId("org.cassandraunit")
      .artifactId("cassandra-unit")
      .versionSlug("cassandraunit")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private SeedDestination toSrcMainResourcesCql() {
    return toSrcMainResources().append("config").append("cql");
  }

  private SeedDestination toSrcMainDockerScripts() {
    return toSrcMainDocker().append(CASSANDRA).append("scripts");
  }
}
