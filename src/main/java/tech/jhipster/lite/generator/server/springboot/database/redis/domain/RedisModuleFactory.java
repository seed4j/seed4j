package tech.jhipster.lite.generator.server.springboot.database.redis.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class RedisModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/database/redis");

  private static final String REDIS_SECONDARY = "wire/redis/infrastructure/secondary";
  private static final String DOCKER_COMPOSE_COMMAND = "docker compose -f src/main/docker/redis.yml up -d";
  private static final String REFLECTIONS_GROUP = "org.reflections";

  private final DockerImages dockerImages;

  public RedisModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.basePackage().get() + ".";

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Redis"), SOURCE.template("redis.md"))
      .startupCommand(startupCommand())
      .context()
        .put("redisDockerImage", dockerImages.get("redis").fullName())
        .and()
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-redis"))
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
        .set(propertyKey("spring.data.redis.database"), propertyValue("0"))
        .set(propertyKey("spring.data.redis.url"), propertyValue("redis://localhost:6379"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.data.redis.url"), propertyValue("${TEST_REDIS_URL}"))
        .and()
      .springTestFactories()
        .append(propertyKey("org.springframework.context.ApplicationListener"), propertyValue(packageName + "TestRedisManager"))
        .and()
      .springMainLogger(REFLECTIONS_GROUP, LogLevel.WARN)
      .springMainLogger("org.springframework.data.redis", LogLevel.WARN)
      .springTestLogger(REFLECTIONS_GROUP, LogLevel.WARN)
      .springTestLogger("redis.clients.jedis", LogLevel.WARN)
      .springTestLogger("com.github.dockerjava", LogLevel.WARN)
      .springTestLogger("org.testcontainers", LogLevel.WARN)
      .build();
    //@formatter:on
  }

  private String startupCommand() {
    return DOCKER_COMPOSE_COMMAND;
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
