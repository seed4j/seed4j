package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain;

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
class SpringCloudConfigModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private SpringCloudConfigModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("jhipster/jhipster-registry")).thenReturn(new DockerImage("jhipster/jhipster-registry", "1.1.1"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<spring-cloud.version>")
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
                  <artifactId>spring-cloud-starter-config</artifactId>
                </dependency>
            """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap.properties")
      .containing("spring.application.name=myApp")
      .containing("jhipster.registry.password=admin")
      .containing("spring.cloud.compatibility-verifier.enabled=false")
      .containing("spring.cloud.config.label=main")
      .containing("spring.cloud.config.name=myApp")
      .containing("spring.cloud.config.retry.initial-interval=1000")
      .containing("spring.cloud.config.retry.max-attempts=100")
      .containing("spring.cloud.config.retry.max-interval=2000")
      .containing("spring.cloud.config.uri=http://admin:${jhipster.registry.password}@localhost:8761/config")
      .containing("spring.cloud.config.fail-fast=true")
      .and()
      .hasFile("src/main/resources/config/bootstrap-local.properties")
      .containing("spring.application.name=myApp")
      .containing("jhipster.registry.password=admin")
      .containing("spring.cloud.compatibility-verifier.enabled=false")
      .containing("spring.cloud.config.label=main")
      .containing("spring.cloud.config.name=myApp")
      .containing("spring.cloud.config.retry.initial-interval=1000")
      .containing("spring.cloud.config.retry.max-attempts=100")
      .containing("spring.cloud.config.retry.max-interval=2000")
      .containing("spring.cloud.config.uri=http://admin:${jhipster.registry.password}@localhost:8761/config")
      .containing("spring.cloud.config.fail-fast=false")
      .and()
      .hasFile("src/test/resources/config/bootstrap.properties")
      .containing("spring.application.name=myApp")
      .containing("spring.cloud.config.enabled=false")
      .and()
      .hasFiles("src/main/docker/jhipster-registry.yml", "src/main/docker/central-server-config/localhost-config/application.properties");
  }
}
