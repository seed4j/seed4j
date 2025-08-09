package com.seed4j.generator.server.springboot.database.cassandra.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.comment;
import static com.seed4j.module.domain.SeedModule.dockerComposeFile;
import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.javaDependency;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcMainDocker;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CassandraModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/database/cassandra");
  private static final String CASSANDRA_SECONDARY = "wire/cassandra/infrastructure/secondary";
  private static final String DC = "datacenter1";
  private static final PropertyKey LOCAL_DATACENTER_PROPERTY = propertyKey("spring.cassandra.local-datacenter");
  private final DockerImages dockerImages;

  public CassandraModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.basePackage().get() + ".";

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-cassandra"))
        .addDependency(testContainerDependency())
        .and()
      .documentation(documentationTitle("Cassandra"), SOURCE.file("cassandra.md"))
      .startupCommands()
        .dockerCompose("src/main/docker/cassandra.yml")
        .and()
      .context()
        .put("cassandraDockerImage", dockerImages.get("cassandra").fullName())
        .put("DC", DC)
        .and()
      .files()
        .batch(SOURCE, toSrcMainJava().append(packagePath).append(CASSANDRA_SECONDARY))
          .addTemplate("CassandraDatabaseConfiguration.java")
          .addTemplate("CassandraJSR310DateConverters.java")
          .and()
        .add(
          SOURCE.template("CassandraJSR310DateConvertersTest.java"),
          toSrcTestJava().append(packagePath).append(CASSANDRA_SECONDARY).append("CassandraJSR310DateConvertersTest.java")
        )
        .add(SOURCE.template("cassandra.yml"), toSrcMainDocker().append("cassandra.yml"))
        .add(SOURCE.template("TestCassandraManager.java"), toSrcTestJava().append(packagePath).append("TestCassandraManager.java"))
        .add(SOURCE.template("CassandraKeyspaceIT.java"), toSrcTestJava().append(packagePath).append("CassandraKeyspaceIT.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.cassandra.contact-points"), propertyValue("127.0.0.1"))
        .comment(LOCAL_DATACENTER_PROPERTY, comment("keyspace-name: yourKeyspace"))
        .set(propertyKey("spring.cassandra.port"), propertyValue(9042))
        .set(LOCAL_DATACENTER_PROPERTY, propertyValue(DC))
        .set(propertyKey("spring.cassandra.schema-action"), propertyValue("none"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.cassandra.port"), propertyValue("${TEST_CASSANDRA_PORT}"))
        .set(propertyKey("spring.cassandra.contact-points"), propertyValue("${TEST_CASSANDRA_CONTACT_POINT}"))
        .set(LOCAL_DATACENTER_PROPERTY, propertyValue("${TEST_CASSANDRA_DC}"))
        .set(propertyKey("spring.cassandra.keyspace-name"), propertyValue("${TEST_CASSANDRA_KEYSPACE}"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestCassandraManager"))
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/cassandra.yml"))
        .and()
      .springMainLogger("com.datastax", LogLevel.WARN)
      .springTestLogger("com.datastax", LogLevel.WARN)
      .springTestLogger("org.testcontainers", LogLevel.WARN)
      .build();
    // @formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("cassandra")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
