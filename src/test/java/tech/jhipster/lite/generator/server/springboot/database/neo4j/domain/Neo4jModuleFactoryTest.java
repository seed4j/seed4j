package tech.jhipster.lite.generator.server.springboot.database.neo4j.domain;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.logbackFile;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.readmeFile;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.testLogbackFile;

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
class Neo4jModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private Neo4jModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("neo4j")).thenReturn(new DockerImageVersion("neo4j", "4.4.11-community"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile(), readmeFile())
      .hasFiles("documentation/neo4j-db.md")
      .hasFile("README.md")
      .containing("""
            ```bash
            docker compose -f src/main/docker/neo4j.yml up -d
            ```
            """)
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
                  <artifactId>neo4j</artifactId>
                  <version>${testcontainers.version}</version>
                  <scope>test</scope>
                </dependency>
            """
      )
      .and()
      .hasFile("src/main/docker/neo4j.yml")
      .containing("neo4j:4.4.11-community")
      .and()
      .hasFiles("src/test/java/com/jhipster/test/TestNeo4jManager.java")
      .hasFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.context.ApplicationListener=com.jhipster.test")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.neo4j.pool.metrics-enabled")
      .containing("spring.neo4j.uri=bolt://localhost:7687")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("spring.neo4j.uri=${TEST_NEO4J_URI}")
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
