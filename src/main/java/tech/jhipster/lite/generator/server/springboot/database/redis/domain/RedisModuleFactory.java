package tech.jhipster.lite.generator.server.springboot.database.redis.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.artifactId;
import static tech.jhipster.lite.module.domain.JHipsterModule.dockerComposeFile;
import static tech.jhipster.lite.module.domain.JHipsterModule.documentationTitle;
import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.groupId;
import static tech.jhipster.lite.module.domain.JHipsterModule.javaDependency;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyValue;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcMainDocker;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcMainJava;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcTestJava;
import static tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope.RUNTIME;

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
  private static final String REFLECTIONS_GROUP = "org.reflections";
  private static final String SPRING_BOOT_GROUP = "org.springframework.boot";
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
      .startupCommands()
        .dockerCompose("src/main/docker/redis.yml")
        .and()
      .context()
        .put("redisDockerImage", dockerImages.get("redis").fullName())
        .and()
      .javaDependencies()
        .addDependency(groupId(SPRING_BOOT_GROUP), artifactId("spring-boot-starter-data-redis"))
        .addDependency(dockerComposeIntegration())
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
    //@formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("testcontainers")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency dockerComposeIntegration() {
    return JavaDependency.builder().groupId(SPRING_BOOT_GROUP).artifactId("spring-boot-docker-compose").scope(RUNTIME).optional().build();
  }

  private JavaDependency reflectionsDependency() {
    return javaDependency().groupId(REFLECTIONS_GROUP).artifactId("reflections").versionSlug("reflections").build();
  }
}
