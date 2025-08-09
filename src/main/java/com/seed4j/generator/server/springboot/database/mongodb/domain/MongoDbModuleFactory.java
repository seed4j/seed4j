package com.seed4j.generator.server.springboot.database.mongodb.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
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
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MongoDbModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/database/mongodb");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String MONGO_SECONDARY = "wire/mongodb/infrastructure/secondary";
  private static final String REFLECTIONS_GROUP = "org.reflections";

  private final DockerImages dockerImages;

  public MongoDbModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.basePackage().get() + ".";

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Mongo DB"), SOURCE.template("mongodb.md"))
      .startupCommands()
        .dockerCompose("src/main/docker/mongodb.yml")
        .and()
      .context()
        .put("mongodbDockerImage", dockerImages.get("mongo").fullName())
        .and()
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-mongodb"))
        .addDependency(reflectionsDependency())
        .addDependency(testContainerDependency())
        .and()
      .files()
        .add(SOURCE.template("mongodb.yml"), toSrcMainDocker().append("mongodb.yml"))
        .batch(MAIN_SOURCE, toSrcMainJava().append(packagePath).append(MONGO_SECONDARY))
          .addTemplate("MongodbDatabaseConfiguration.java")
          .addTemplate("JSR310DateConverters.java")
          .and()
        .add(
              TEST_SOURCE.template("JSR310DateConvertersTest.java"),
              toSrcTestJava().append(packagePath).append(MONGO_SECONDARY).append("JSR310DateConvertersTest.java")
            )
        .add(TEST_SOURCE.template("TestMongoDBManager.java"), toSrcTestJava().append(packagePath).append("TestMongoDBManager.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.data.mongodb.database"), propertyValue(properties.projectBaseName().get()))
        .set(propertyKey("spring.data.mongodb.uri"), propertyValue("mongodb://localhost:27017/" + properties.projectBaseName().get()))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.data.mongodb.uri"), propertyValue("${TEST_MONGODB_URI}"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestMongoDBManager"))
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/mongodb.yml"))
        .and()
      .springMainLogger(REFLECTIONS_GROUP, LogLevel.WARN)
      .springMainLogger("org.mongodb.driver", LogLevel.WARN)
      .springTestLogger(REFLECTIONS_GROUP, LogLevel.WARN)
      .springTestLogger("org.mongodb.driver", LogLevel.WARN)
      .springTestLogger("com.github.dockerjava", LogLevel.WARN)
      .springTestLogger("org.testcontainers", LogLevel.WARN)
      .build();
    // @formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("mongodb")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency reflectionsDependency() {
    return javaDependency().groupId(REFLECTIONS_GROUP).artifactId("reflections").versionSlug("reflections").build();
  }
}
