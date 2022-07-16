package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class MongoDbModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/database/mongodb");

  private static final String MONGO_SECONDARY = "technical/infrastructure/secondary/mongodb";

  private final DockerImages dockerImages;

  public MongoDbModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Mongo DB"), SOURCE.template("mongodb.md"))
      .readmeSection(dockerComposeDocumentation())
      .context()
        .put("mongodbDockerImage", dockerImages.get("mongo").fullName())
        .and()
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-mongodb"))
        .addDependency(groupId("org.mongodb"), artifactId("mongodb-driver-sync"))
        .addDependency(testContainerDependency())
        .and()
      .files()
        .add(SOURCE.template("mongodb.yml"), toSrcMainDocker().append("mongodb.yml"))
        .batch(SOURCE, toSrcMainJava().append(packagePath).append(MONGO_SECONDARY))
          .template("MongodbDatabaseConfiguration.java")
          .template("JSR310DateConverters.java")
          .and()
        .add(
              SOURCE.template("JSR310DateConvertersTest.java"), 
              toSrcTestJava().append(packagePath).append(MONGO_SECONDARY).append("JSR310DateConvertersTest.java")
            )
        .batch(SOURCE, toSrcTestJava().append(packagePath))
          .template("MongodbTestContainerExtension.java")
          .template("TestContainersSpringContextCustomizerFactory.java")
          .and()
        .add(SOURCE.template("spring.factories"), to("src/test/resources/META-INF/spring.factories"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.data.mongodb.database"), propertyValue(properties.basePackage().get()))
        .set(propertyKey("spring.data.mongodb.uri"), propertyValue("mongodb://localhost:27017"))
        .and()
      .springMainLogger("org.reflections", LogLevel.WARN)
      .springMainLogger("org.mongodb.driver", LogLevel.WARN)
      .springTestLogger("org.reflections", LogLevel.WARN)
      .springTestLogger("org.mongodb.driver", LogLevel.WARN)
      .springTestLogger("com.github.dockerjava", LogLevel.WARN)
      .springTestLogger("org.testcontainers", LogLevel.WARN)
      .integrationTestExtension("MongodbTestContainerExtension")
      .build();
    //@formatter:on
  }

  private String dockerComposeDocumentation() {
    return """
        ```bash
        docker-compose -f src/main/docker/mongodb.yml up -d
        ```
        """;
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("mongodb")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
