package com.seed4j.generator.server.springboot.database.redis;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import org.junit.jupiter.api.Test;

@UnitTest
class RedisModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final RedisModuleFactory factory = new RedisModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildModule() {
    seed4JModuleServices.dockerImages().put("redis", "1.1.1");

    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile(), readmeFile())
      .hasFiles("documentation/redis.md")
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/redis.yml up -d
        ```
        """
      )
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-redis</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.testcontainers</groupId>
              <artifactId>testcontainers</artifactId>
              <version>${testcontainers.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.reflections</groupId>
              <artifactId>reflections</artifactId>
              <version>${reflections.version}</version>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/docker/redis.yml")
      .containing("redis:1.1.1")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/redis.yml")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/wire/redis/infrastructure/secondary",
        "RedisDatabaseConfiguration.java",
        "JSR310DateConverters.java"
      )
      .hasFiles("src/test/java/com/seed4j/growth/wire/redis/infrastructure/secondary/JSR310DateConvertersTest.java")
      .hasFiles("src/test/java/com/seed4j/growth/TestRedisManager.java")
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.seed4j.growth")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          data:
            redis:
              database: 0
              url: redis://localhost:6379
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          data:
            redis:
              url: ${TEST_REDIS_URL}
        """
      )
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.reflections\" level=\"WARN\" />")
      .containing("<logger name=\"org.springframework.data.redis\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.reflections\" level=\"WARN\" />")
      .containing("<logger name=\"redis.clients.jedis\" level=\"WARN\" />")
      .containing("<logger name=\"com.github.dockerjava\" level=\"WARN\" />")
      .containing("<logger name=\"org.testcontainers\" level=\"WARN\" />");
  }
}
