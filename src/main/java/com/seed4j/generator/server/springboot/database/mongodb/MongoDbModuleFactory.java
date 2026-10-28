package com.seed4j.generator.server.springboot.database.mongodb;

import static com.seed4j.generator.server.springboot.common.domain.SpringBootLoggers.springMainLogger;
import static com.seed4j.generator.server.springboot.common.domain.SpringBootLoggers.springTestLogger;
import static com.seed4j.generator.server.springboot.common.domain.SpringBootProperties.SPRING_MAIN;
import static com.seed4j.generator.server.springboot.common.domain.SpringBootProperties.SPRING_TEST;
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
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.error.domain.Assert;

class MongoDbModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/database/mongodb");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final String MONGO_SECONDARY = "wire/mongodb/infrastructure/secondary";
  private static final String REFLECTIONS_GROUP = "org.reflections";

  private final DockerImages dockerImages;

  MongoDbModuleFactory(Seed4JModuleServices services) {
    this.dockerImages = services.dockerImages();
  }

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
          .nullMarkedJavaPackage(toSrcMainJava().append(packagePath).append(MONGO_SECONDARY))
        .add(
              TEST_SOURCE.template("JSR310DateConvertersTest.java"),
              toSrcTestJava().append(packagePath).append(MONGO_SECONDARY).append("JSR310DateConvertersTest.java")
            )
        .add(TEST_SOURCE.template("TestMongoDBManager.java"), toSrcTestJava().append(packagePath).append("TestMongoDBManager.java"))
        .and()
      .applicationProperties(SPRING_MAIN)
        .set(propertyKey("spring.mongodb.database"), propertyValue(properties.projectBaseName().get()))
        .set(propertyKey("spring.mongodb.uri"), propertyValue("mongodb://localhost:27017/" + properties.projectBaseName().get()))
        .set(propertyKey("spring.mongodb.representation.uuid"), propertyValue("standard"))
        .and()
      .applicationProperties(SPRING_TEST)
        .set(propertyKey("spring.mongodb.uri"), propertyValue("${TEST_MONGODB_URI}"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestMongoDBManager"))
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/mongodb.yml"))
        .and()
      .with(springMainLogger(REFLECTIONS_GROUP, LogLevel.WARN))
      .with(springMainLogger("org.mongodb.driver", LogLevel.WARN))
      .with(springTestLogger(REFLECTIONS_GROUP, LogLevel.WARN))
      .with(springTestLogger("org.mongodb.driver", LogLevel.WARN))
      .with(springTestLogger("com.github.dockerjava", LogLevel.WARN))
      .with(springTestLogger("org.testcontainers", LogLevel.WARN))
      .build();
    // @formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("testcontainers-mongodb")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency reflectionsDependency() {
    return javaDependency().groupId(REFLECTIONS_GROUP).artifactId("reflections").versionSlug("reflections").build();
  }
}
