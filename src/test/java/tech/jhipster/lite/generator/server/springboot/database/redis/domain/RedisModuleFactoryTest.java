package tech.jhipster.lite.generator.server.springboot.database.redis.domain;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile(), readmeFile())
      .hasFiles("documentation/redis.md")
      .hasFile("README.md")
      .containing("""
            ```bash
            docker compose -f src/main/docker/redis.yml up -d
            ```
            """)
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
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/wire/redis/infrastructure/secondary",
        "RedisDatabaseConfiguration.java",
        "JSR310DateConverters.java"
      )
      .hasFiles("src/test/java/com/jhipster/test/wire/redis/infrastructure/secondary/JSR310DateConvertersTest.java")
      .hasFiles("src/test/java/com/jhipster/test/TestRedisManager.java")
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.jhipster.test")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.data.redis.database=0")
      .containing("spring.data.redis.url=redis://localhost:6379")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("spring.data.redis.url=${TEST_REDIS_URL}")
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
