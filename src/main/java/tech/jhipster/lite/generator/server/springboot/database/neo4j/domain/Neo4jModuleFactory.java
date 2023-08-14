package tech.jhipster.lite.generator.server.springboot.database.neo4j.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.*;
import tech.jhipster.lite.module.domain.docker.*;
import tech.jhipster.lite.module.domain.file.*;
import tech.jhipster.lite.module.domain.javadependency.*;
import tech.jhipster.lite.module.domain.properties.*;
import tech.jhipster.lite.shared.error.domain.*;

public class Neo4jModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/database/neo4j");

  private static final String NEO4J_SECONDARY = "technical/infrastructure/secondary/neo4j";
  private static final String DOCKER_COMPOSE_COMMAND = "docker compose -f src/main/docker/neo4j.yml up -d";

  private final DockerImages dockerImages;

  public Neo4jModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.basePackage().get() + ".";

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Neo4j DB"), SOURCE.template("neo4j.md"))
      .startupCommand(DOCKER_COMPOSE_COMMAND)
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
        .set(propertyKey("spring.neo4j.pool.metrics-enabled"), propertyValue("true"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.neo4j.uri"), propertyValue("${TEST_NEO4J_URI}"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestNeo4jManager"))
        .and()
      .springMainLogger("org.neo4j.driver", LogLevel.WARN)
      .springTestLogger("org.neo4j.driver", LogLevel.WARN)
      .springTestLogger("com.github.dockerjava", LogLevel.WARN)
      .springTestLogger("org.testcontainers", LogLevel.WARN)
      .build();
    //@formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("neo4j")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
