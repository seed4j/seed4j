package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

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
class CassandraModuleFactoryTest {

  private static final String DC = "datacenter1";

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private CassandraModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("cassandra")).thenReturn(new DockerImageVersion("cassandra", "4.0.7"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), readmeFile())
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
      .containing("""
        ```bash
        docker compose -f src/main/docker/cassandra.yml up -d
        ```
        """)
      .and()
      .hasFile("src/main/docker/cassandra.yml")
      .containing("cassandra:4.0.7")
      .containing("CASSANDRA_DC=" + DC)
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.data.cassandra.contact-points=127.0.0.1")
      .containing("spring.data.cassandra.port=9042")
      .containing("spring.data.cassandra.local-datacenter=" + DC)
      .containing("spring.data.cassandra.schema-action=none")
      .and()
      .hasFiles("src/test/java/com/jhipster/test/CassandraKeyspaceIT.java")
      .hasFile("src/test/java/com/jhipster/test/TestCassandraManager.java")
      .containing("cassandra:4.0.7")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("spring.data.cassandra.port=${TEST_CASSANDRA_PORT}")
      .containing("spring.data.cassandra.contact-points=${TEST_CASSANDRA_CONTACT_POINT}")
      .containing("spring.data.cassandra.local-datacenter=${TEST_CASSANDRA_DC}")
      .containing("spring.data.cassandra.keyspace-name=${TEST_CASSANDRA_KEYSPACE}")
      .containing("spring.data.cassandra.schema-action=create_if_not_exists")
      .and()
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.jhipster.test");
  }
}
