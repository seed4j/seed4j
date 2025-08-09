package com.seed4j.generator.server.springboot.logging.logstash.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class LogstashModuleFactoryTest {

  private static final LogstashModuleFactory factory = new LogstashModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

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
        "src/main/java/com/seed4j/growth/wire/logstash/infrastructure/secondary",
        "LogstashTcpConfiguration.java",
        "LogstashTcpLifeCycle.java",
        "LogstashTcpProperties.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/wire/logstash/infrastructure/secondary",
        "LogstashTcpConfigurationIT.java",
        "LogstashTcpConfigurationTest.java",
        "LogstashTcpLifeCycleTest.java",
        "LogstashTcpPropertiesTest.java"
      )
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        application:
          logging:
            logstash:
              tcp:
                enabled: false
                host: localhost
                port: 5000
                ring-buffer-size: 8192
                shutdown_grace_period: PT1M
        """
      )
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"net.logstash.logback\" level=\"ERROR\" />")
      .containing("<logger name=\"org.jboss.logging\" level=\"WARN\" />");
  }
}
