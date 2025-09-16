package com.seed4j.generator.server.springboot.database.redis.domain;

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

public class RedisModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/database/redis");

  private static final String REDIS_SECONDARY = "wire/redis/infrastructure/secondary";
  private static final String REFLECTIONS_GROUP = "org.reflections";
  private static final String SPRING_BOOT_GROUP = "org.springframework.boot";
  private final DockerImages dockerImages;

  public RedisModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.basePackage().get() + ".";

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Redis"), SOURCE.template("redis.md"))
      .startupCommands()
        .dockerCompose("src/main/docker/redis.yml")
        .and()
      .context()
        .put("redisDockerImage", dockerImages.get("redis").fullName())
        .and()
      .javaDependencies()
        .addDependency(groupId(SPRING_BOOT_GROUP), artifactId("spring-boot-starter-data-redis"))
        .addDependency(reflectionsDependency())
        .addDependency(testContainerDependency())
        .and()
      .files()
        .add(SOURCE.template("redis.yml"), toSrcMainDocker().append("redis.yml"))
        .batch(SOURCE, toSrcMainJava().append(packagePath).append(REDIS_SECONDARY))
          .addTemplate("RedisDatabaseConfiguration.java")
          .addTemplate("JSR310DateConverters.java")
          .and()
        .add(
              SOURCE.template("JSR310DateConvertersTest.java"),
              toSrcTestJava().append(packagePath).append(REDIS_SECONDARY).append("JSR310DateConvertersTest.java")
            )
        .add(SOURCE.template("TestRedisManager.java"), toSrcTestJava().append(packagePath).append("TestRedisManager.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.data.redis.database"), propertyValue(0))
        .set(propertyKey("spring.data.redis.url"), propertyValue("redis://localhost:6379"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.data.redis.url"), propertyValue("${TEST_REDIS_URL}"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestRedisManager"))
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/redis.yml"))
        .and()
      .springMainLogger(REFLECTIONS_GROUP, LogLevel.WARN)
      .springMainLogger("org.springframework.data.redis", LogLevel.WARN)
      .springTestLogger(REFLECTIONS_GROUP, LogLevel.WARN)
      .springTestLogger("redis.clients.jedis", LogLevel.WARN)
      .springTestLogger("com.github.dockerjava", LogLevel.WARN)
      .springTestLogger("org.testcontainers", LogLevel.WARN)
      .build();
    // @formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("testcontainers")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency reflectionsDependency() {
    return javaDependency().groupId(REFLECTIONS_GROUP).artifactId("reflections").versionSlug("reflections").build();
  }
}
