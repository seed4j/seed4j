package com.seed4j.generator.server.springboot.springcloud.eureka;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import org.junit.jupiter.api.Test;

@UnitTest
class EurekaModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final EurekaModuleFactory factory = new EurekaModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    seed4JModuleServices.dockerImages().put("jhipster/jhipster-registry", "1.1.1");

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
              <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
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
