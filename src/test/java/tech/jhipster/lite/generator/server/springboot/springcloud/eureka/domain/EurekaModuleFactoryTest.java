package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class EurekaModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private EurekaModuleFactory factory;

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    when(dockerImages.get("jhipster/jhipster-registry")).thenReturn(new DockerImage("jhipster/jhipster-registry", "1.1.1"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), propertiesFile())
      .hasFile("pom.xml")
      .containing("<spring-cloud.version>")
      .containing("<spring-cloud-netflix-eureka-client.version>")
      .containing(
        """
              <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <scope>import</scope>
                <type>pom</type>
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
      .hasFile("src/main/resources/config/bootstrap.properties")
      .containing("spring.application.name=myApp")
      .containing("spring.cloud.compatibility-verifier.enabled=false")
      .containing("eureka.client.service-url.defaultZone=http://admin:admin@localhost:8761/eureka")
      .containing("eureka.client.enabled=true")
      .containing("eureka.client.healthcheck.enabled=true")
      .containing("eureka.client.fetch-registry=true")
      .containing("eureka.client.register-with-eureka=true")
      .containing("eureka.client.instance-info-replication-interval-seconds=10")
      .containing("eureka.client.registry-fetch-interval-seconds=10")
      .containing("eureka.instance.appname=myapp")
      .containing("eureka.instance.instance-id=myapp:${spring.application.instance-id:${random.value}}")
      .containing("eureka.instance.lease-renewal-interval-in-seconds=5")
      .containing("eureka.instance.lease-expiration-duration-in-seconds=10")
      .containing("eureka.instance.status-page-url-path=${management.endpoints.web.base-path}/info")
      .containing("eureka.instance.health-check-url-path=${management.endpoints.web.base-path}/health")
      .and()
      .hasFile("src/test/resources/config/bootstrap.properties")
      .containing("spring.application.name=myApp")
      .containing("spring.cloud.compatibility-verifier.enabled=false")
      .containing("eureka.client.enabled=false")
      .and()
      .hasFile("src/main/docker/jhipster-registry.yml")
      .containing("jhipster/jhipster-registry:1.1.1")
      .and()
      .hasFile("src/main/docker/central-server-config/localhost-config/application.properties");
  }
}
