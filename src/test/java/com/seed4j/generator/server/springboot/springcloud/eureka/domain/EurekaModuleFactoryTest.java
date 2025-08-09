package com.seed4j.generator.server.springboot.springcloud.eureka.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
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
class EurekaModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private EurekaModuleFactory factory;

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    when(dockerImages.get("jhipster/jhipster-registry")).thenReturn(new DockerImageVersion("jhipster/jhipster-registry", "1.1.1"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<spring-cloud.version>")
      .containing("<spring-cloud-netflix-eureka-client.version>")
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
              <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
              <version>${spring-cloud-netflix-eureka-client.version}</version>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap.yml")
      .containing(
        """
        eureka:
          client:
            enabled: true
            fetch-registry: true
            healthcheck:
              enabled: true
            instance-info-replication-interval-seconds: 10
            register-with-eureka: true
            registry-fetch-interval-seconds: 10
            service-url:
              defaultZone: http://admin:admin@localhost:8761/eureka
          instance:
            appname: myapp
            health-check-url-path: ${management.endpoints.web.base-path}/health
            instance-id: myapp:${spring.application.instance-id:${random.value}}
            lease-expiration-duration-in-seconds: 10
            lease-renewal-interval-in-seconds: 5
            status-page-url-path: ${management.endpoints.web.base-path}/info
        spring:
          application:
            name: myApp
          cloud:
            compatibility-verifier:
              enabled: false
        """
      )
      .and()
      .hasFile("src/test/resources/config/bootstrap.yml")
      .containing(
        """
        eureka:
          client:
            enabled: false
        spring:
          application:
            name: myApp
          cloud:
            compatibility-verifier:
              enabled: false
        """
      )
      .and()
      .hasFile("src/main/docker/jhipster-registry.yml")
      .containing("jhipster/jhipster-registry:1.1.1")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/jhipster-registry.yml")
      .and()
      .hasFile("src/main/docker/central-server-config/localhost-config/application.properties");
  }
}
