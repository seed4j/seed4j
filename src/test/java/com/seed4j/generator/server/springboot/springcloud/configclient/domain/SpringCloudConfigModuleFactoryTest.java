package com.seed4j.generator.server.springboot.springcloud.configclient.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
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
class SpringCloudConfigModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private SpringCloudConfigModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("jhipster/jhipster-registry")).thenReturn(new DockerImageVersion("jhipster/jhipster-registry", "1.1.1"));

    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<spring-cloud.version>")
      .containing(
        """
              <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-bootstrap</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-config</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap.yml")
      .containing(
        """
        jhipster:
          registry:
            password: admin
        spring:
          application:
            name: myApp
          cloud:
            compatibility-verifier:
              enabled: false
            config:
              fail-fast: true
              label: main
              name: myApp
              retry:
                initial-interval: 1000
                max-attempts: 100
                max-interval: 2000
              uri: http://admin:${jhipster.registry.password}@localhost:8761/config
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap-local.yml")
      .containing(
        """
        jhipster:
          registry:
            password: admin
        spring:
          application:
            name: myApp
          cloud:
            compatibility-verifier:
              enabled: false
            config:
              fail-fast: false
              label: main
              name: myApp
              retry:
                initial-interval: 1000
                max-attempts: 100
                max-interval: 2000
              uri: http://admin:${jhipster.registry.password}@localhost:8761/config
        """
      )
      .and()
      .hasFile("src/test/resources/config/bootstrap.yml")
      .containing(
        """
        spring:
          application:
            name: myApp
          cloud:
            config:
              enabled: false
        """
      )
      .and()
      .hasFiles("src/main/docker/jhipster-registry.yml", "src/main/docker/central-server-config/localhost-config/application.properties");
  }
}
