package com.seed4j.generator.server.springboot.database.cassandra.domain;

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
class CassandraModuleFactoryTest {

  private static final String DC = "datacenter1";

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private CassandraModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("cassandra")).thenReturn(new DockerImageVersion("cassandra", "4.0.7"));

    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), readmeFile(), logbackFile(), testLogbackFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-cassandra</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.testcontainers</groupId>
              <artifactId>cassandra</artifactId>
              <version>${testcontainers.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFile("documentation/cassandra.md")
      .and()
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/cassandra.yml up -d
        ```
        """
      )
      .and()
      .hasFile("src/main/docker/cassandra.yml")
      .containing("cassandra:4.0.7")
      .containing("CASSANDRA_DC=" + DC)
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/cassandra.yml")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        // language=yaml
        """
        spring:
          cassandra:
            contact-points: 127.0.0.1
            # keyspace-name: yourKeyspace
            local-datacenter: datacenter1
            port: 9042
            schema-action: none
        """
      )
      .and()
      .hasFiles("src/test/java/com/seed4j/growth/CassandraKeyspaceIT.java")
      .hasFile("src/test/java/com/seed4j/growth/TestCassandraManager.java")
      .containing("cassandra:4.0.7")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/wire/cassandra/infrastructure/secondary",
        "CassandraDatabaseConfiguration.java",
        "CassandraJSR310DateConverters.java"
      )
      .hasFiles("src/test/java/com/seed4j/growth/wire/cassandra/infrastructure/secondary/CassandraJSR310DateConvertersTest.java")
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        // language=yaml
        """
        spring:
          cassandra:
            contact-points: ${TEST_CASSANDRA_CONTACT_POINT}
            keyspace-name: ${TEST_CASSANDRA_KEYSPACE}
            local-datacenter: ${TEST_CASSANDRA_DC}
            port: ${TEST_CASSANDRA_PORT}
        """
      )
      .and()
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.seed4j.growth.TestCassandraManager")
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"com.datastax\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"com.datastax\" level=\"WARN\" />")
      .containing("<logger name=\"org.testcontainers\" level=\"WARN\" />");
  }
}
