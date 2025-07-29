package com.seed4j.generator.server.springboot.database.redis.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class RedisModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private RedisModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("redis")).thenReturn(new DockerImageVersion("redis", "1.1.1"));

    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .build();

    JHipsterModule module = factory.buildModule(properties);

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
        "src/main/java/tech/jhipster/jhlitest/wire/redis/infrastructure/secondary",
        "RedisDatabaseConfiguration.java",
        "JSR310DateConverters.java"
      )
      .hasFiles("src/test/java/tech/jhipster/jhlitest/wire/redis/infrastructure/secondary/JSR310DateConvertersTest.java")
      .hasFiles("src/test/java/tech/jhipster/jhlitest/TestRedisManager.java")
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=tech.jhipster.jhlitest")
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
