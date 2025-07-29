package com.seed4j.generator.server.springboot.broker.pulsar.domain;

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
class PulsarModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private PulsarModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("apachepulsar/pulsar")).thenReturn(new DockerImageVersion("apachepulsar/pulsar", "1.1.1"));

    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), integrationTestAnnotation(), readmeFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-pulsar</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.testcontainers</groupId>
              <artifactId>pulsar</artifactId>
              <version>${testcontainers.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/docker/pulsar.yml")
      .containing("apachepulsar/pulsar:1.1.1")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        pulsar:
          client:
            service-url: pulsar://localhost:6650
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        pulsar:
          client:
            num-io-threads: 8
          consumer:
            subscription-name: test-subscription
            topic-names[0]: test-topic
          producer:
            topic-name: test-topic
        """
      )
      .and()
      .hasJavaTests("tech/jhipster/jhlitest/PulsarTestContainerExtension.java")
      .hasFile("src/test/java/tech/jhipster/jhlitest/IntegrationTest.java")
      .containing("import org.junit.jupiter.api.extension.ExtendWith;")
      .containing("@ExtendWith(PulsarTestContainerExtension.class)")
      .and()
      .hasPrefixedFiles(
        "src/main/java/tech/jhipster/jhlitest/wire/pulsar/infrastructure/config",
        "PulsarProperties.java",
        "PulsarConfiguration.java"
      )
      .hasFiles("src/test/java/tech/jhipster/jhlitest/wire/pulsar/infrastructure/config/PulsarConfigurationIT.java")
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/pulsar.yml up -d
        ```
        """
      );
  }

  private ModuleFile integrationTestAnnotation() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/tech/jhipster/jhlitest/IntegrationTest.java");
  }
}
