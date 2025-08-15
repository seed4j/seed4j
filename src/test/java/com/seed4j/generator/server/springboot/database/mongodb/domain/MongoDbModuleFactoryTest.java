package com.seed4j.generator.server.springboot.database.mongodb.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MongoDbModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private MongoDbModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("mongo")).thenReturn(new DockerImageVersion("mongo", "1.1.1"));

    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile(), readmeFile())
      .hasFiles("documentation/mongo-db.md")
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/mongodb.yml up -d
        ```
        """
      )
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-mongodb</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.testcontainers</groupId>
              <artifactId>mongodb</artifactId>
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
      .hasFile("src/main/docker/mongodb.yml")
      .containing("mongo:1.1.1")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/mongodb.yml")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/wire/mongodb/infrastructure/secondary",
        "MongodbDatabaseConfiguration.java",
        "JSR310DateConverters.java"
      )
      .hasFiles("src/test/java/com/seed4j/growth/wire/mongodb/infrastructure/secondary/JSR310DateConvertersTest.java")
      .hasFiles("src/test/java/com/seed4j/growth/TestMongoDBManager.java")
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.seed4j.growth")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          data:
            mongodb:
              database: seed4j
              uri: mongodb://localhost:27017/seed4j
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          data:
            mongodb:
              uri: ${TEST_MONGODB_URI}
        """
      )
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.reflections\" level=\"WARN\" />")
      .containing("<logger name=\"org.mongodb.driver\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.reflections\" level=\"WARN\" />")
      .containing("<logger name=\"org.mongodb.driver\" level=\"WARN\" />")
      .containing("<logger name=\"com.github.dockerjava\" level=\"WARN\" />")
      .containing("<logger name=\"org.testcontainers\" level=\"WARN\" />");
  }
}
