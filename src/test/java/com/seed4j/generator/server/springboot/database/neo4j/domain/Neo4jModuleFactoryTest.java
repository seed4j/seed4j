package com.seed4j.generator.server.springboot.database.neo4j.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class Neo4jModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private Neo4jModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("neo4j")).thenReturn(new DockerImageVersion("neo4j", "4.4.11-community"));

    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile(), readmeFile())
      .hasFiles("documentation/neo4j-db.md")
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/neo4j.yml up -d
        ```
        """
      )
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-neo4j</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.testcontainers</groupId>
              <artifactId>testcontainers-neo4j</artifactId>
              <version>${testcontainers.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/docker/neo4j.yml")
      .containing("neo4j:4.4.11-community")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/neo4j.yml")
      .and()
      .hasFiles("src/test/java/com/seed4j/growth/TestNeo4jManager.java")
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.seed4j.growth")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          neo4j:
            pool:
              metrics-enabled: true
            uri: bolt://localhost:7687
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          neo4j:
            uri: ${TEST_NEO4J_URI}
        """
      )
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.neo4j.driver\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.neo4j.driver\" level=\"WARN\" />")
      .containing("<logger name=\"com.github.dockerjava\" level=\"WARN\" />")
      .containing("<logger name=\"org.testcontainers\" level=\"WARN\" />");
  }
}
