package com.seed4j.generator.server.springboot.dbmigration.cassandra.domain;

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
class CassandraMigrationModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private CassandraMigrationModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("cassandra")).thenReturn(new DockerImageVersion("cassandra", "4.0.7"));

    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), readmeFile(), springFactoriesTest())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.cassandraunit</groupId>
              <artifactId>cassandra-unit</artifactId>
              <version>${cassandraunit.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/docker/cassandra-migration.yml")
      .hasFiles("src/main/docker/cassandra-migration-spring-docker-compose.yml")
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/cassandra-migration-spring-docker-compose.yml")
      .and()
      .hasFiles("src/main/resources/config/cql/create-migration-keyspace.cql")
      .hasFiles("src/main/resources/config/cql/changelog/README.md")
      .hasFiles("documentation/cassandra-migration.md")
      .hasPrefixedFiles("src/main/docker/cassandra/scripts", "autoMigrate.sh", "execute-cql.sh")
      .hasFiles("src/test/java/com/seed4j/growth/TestCassandraMigrationLoader.java")
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing(
        "org.springframework.context.ApplicationListener=com.seed4j.growth.TestCassandraManager,com.seed4j.growth.TestCassandraMigrationLoader"
      )
      .and()
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/cassandra-migration.yml up -d
        ```
        """
      )
      .and()
      .hasFile("src/main/docker/cassandra/Cassandra-Migration.Dockerfile")
      .containing("cassandra:4.0.7");
  }

  private ModuleFile springFactoriesTest() {
    return file("src/test/resources/projects/cassandra/spring.factories", "src/test/resources/META-INF/spring.factories");
  }
}
