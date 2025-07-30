package com.seed4j.generator.server.springboot.springcloud.consul.domain;

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
class ConsulModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private ConsulModuleFactory factory;

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("growth")
      .build();
    when(dockerImages.get("consul")).thenReturn(new DockerImageVersion("consul", "1.12.2"));
    when(dockerImages.get("jhipster/consul-config-loader")).thenReturn(new DockerImageVersion("jhipster/consul-config-loader", "v0.4.1"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile(), readmeFile())
      .hasFile("src/main/docker/consul.yml")
      .containing("consul:1.12.2")
      .containing("jhipster/consul-config-loader:v0.4.1")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/consul.yml")
      .and()
      .hasFile("src/main/docker/central-server-config/application.yml")
      .and()
      .hasFile("pom.xml")
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
              <artifactId>spring-cloud-starter-consul-discovery</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-consul-config</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap.yml")
      .containing(
        """
        spring:
          application:
            name: growth
          cloud:
            compatibility-verifier:
              enabled: false
            consul:
              config:
                format: yaml
                profile-separator: '-'
                watch:
                  enabled: false
              discovery:
                health-check-path: ${server.servlet.context-path:}/management/health
                instance-id: growth:${spring.application.instance-id:${random.value}}
                prefer-ip-address: true
                service-name: growth
                tags[0]: version=@project.version@
                tags[1]: context-path=${server.servlet.context-path:}
                tags[2]: profile=${spring.profiles.active:}
                tags[3]: git-version=${git.build.version:}
                tags[4]: git-commit=${git.commit.id.abbrev:}
                tags[5]: git-branch=${git.branch:}
              host: localhost
              port: 8500
        """
      )
      .and()
      .hasFile("src/test/resources/config/bootstrap.yml")
      .containing(
        """
        spring:
          cloud:
            compatibility-verifier:
              enabled: false
            consul:
              enabled: false
        """
      )
      .and()
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/consul.yml up -d
        ```
        """
      )
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("  <logger name=\"org.apache\" level=\"ERROR\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("  <logger name=\"org.apache\" level=\"ERROR\" />");
  }
}
