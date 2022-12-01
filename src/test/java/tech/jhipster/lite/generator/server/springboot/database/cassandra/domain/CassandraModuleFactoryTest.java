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
      .and()
      .hasFile("documentation/cassandra.md")
      .and()
      .hasFile("README.md")
      .containing(
        """
            ```bash
            docker compose -f src/main/docker/cassandra.yml.mustache up -d
            ```
            """
      )
      .and()
      .hasFile("src/main/docker/cassandra.yml")
      .containing("cassandra:4.0.7")
      .and()
      .hasFile("src/main/docker/cassandra-init/keyspace.cql")
      .containing("jhipster")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.data.cassandra.keyspace-name=jhipster")
      .containing("spring.data.cassandra.contact-points=127.0.0.1")
      .containing("spring.data.cassandra.port=9042")
      .containing("spring.data.cassandra.local-datacenter=datacenter1")
      .containing("spring.data.cassandra.schema-action=CREATE_IF_NOT_EXISTS");
  }
}
