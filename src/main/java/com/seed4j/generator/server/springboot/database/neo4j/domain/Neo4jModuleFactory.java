package com.seed4j.generator.server.springboot.database.neo4j.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.dockerComposeFile;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainDocker;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class Neo4jModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/database/neo4j");

  private static final String NEO4J_SECONDARY = "technical/infrastructure/secondary/neo4j";

  private final DockerImages dockerImages;

  public Neo4jModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.basePackage().get() + ".";

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Neo4j DB"), SOURCE.template("neo4j.md"))
      .startupCommands()
        .dockerCompose("src/main/docker/neo4j.yml")
        .and()
      .context()
      .put("neo4jDockerImage", dockerImages.get("neo4j").fullName())
        .and()
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-neo4j"))
        .addDependency(testContainerDependency())
        .and()
      .files()
        .add(SOURCE.template("neo4j.yml"), toSrcMainDocker().append("neo4j.yml"))
        .batch(SOURCE, toSrcMainJava().append(packagePath).append(NEO4J_SECONDARY))
        .and()
        .add(SOURCE.template("TestNeo4jManager.java"), toSrcTestJava().append(packagePath).append("TestNeo4jManager.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.neo4j.uri"), propertyValue("bolt://localhost:7687"))
        .set(propertyKey("spring.neo4j.pool.metrics-enabled"), propertyValue(true))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.neo4j.uri"), propertyValue("${TEST_NEO4J_URI}"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestNeo4jManager"))
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/neo4j.yml"))
        .and()
      .springMainLogger("org.neo4j.driver", LogLevel.WARN)
      .springTestLogger("org.neo4j.driver", LogLevel.WARN)
      .springTestLogger("com.github.dockerjava", LogLevel.WARN)
      .springTestLogger("org.testcontainers", LogLevel.WARN)
      .build();
    // @formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("testcontainers-neo4j")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
