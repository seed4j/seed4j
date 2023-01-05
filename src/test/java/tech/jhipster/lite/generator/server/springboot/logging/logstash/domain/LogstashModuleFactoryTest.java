package tech.jhipster.lite.generator.server.springboot.logging.logstash.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class LogstashModuleFactoryTest {

  private static final LogstashModuleFactory factory = new LogstashModuleFactory();

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), testLogbackFile())
      .hasFile("pom.xml")
      .containing(
        """
                <dependency>
                  <groupId>net.logstash.logback</groupId>
                  <artifactId>logstash-logback-encoder</artifactId>
                  <version>${logstash-logback-encoder.version}</version>
                </dependency>
            """
      )
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/secondary/logstash",
        "LogstashTcpConfiguration.java",
        "LogstashTcpLifeCycle.java",
        "LogstashTcpProperties.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/technical/infrastructure/secondary/logstash",
        "LogstashTcpConfigurationIT.java",
        "LogstashTcpConfigurationTest.java",
        "LogstashTcpLifeCycleTest.java",
        "LogstashTcpPropertiesTest.java"
      )
      .hasFile("src/main/resources/config/application.properties")
      .containing("application.logging.logstash.tcp.enabled=false")
      .containing("application.logging.logstash.tcp.host=localhost")
      .containing("application.logging.logstash.tcp.port=5000")
      .containing("application.logging.logstash.tcp.ring-buffer-size=8192")
      .containing("application.logging.logstash.tcp.shutdown_grace_period=PT1M")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"net.logstash.logback\" level=\"ERROR\" />")
      .containing("<logger name=\"org.jboss.logging\" level=\"WARN\" />");
  }
}
