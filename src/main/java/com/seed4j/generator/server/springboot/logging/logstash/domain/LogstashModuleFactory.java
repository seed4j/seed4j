package com.seed4j.generator.server.springboot.logging.logstash.domain;

import static com.seed4j.module.domain.JHipsterModule.*;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class LogstashModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/logging/logstash");

  private static final String LOGSTASH_SECONDARY = "wire/logstash/infrastructure/secondary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("net.logstash.logback"), artifactId("logstash-logback-encoder"), versionSlug("logstash-logback-encoder"))
        .and()
      .files()
        .batch(SOURCE.append("main"), toSrcMainJava().append(packagePath).append(LOGSTASH_SECONDARY))
          .addTemplate("LogstashTcpConfiguration.java")
          .addTemplate("LogstashTcpLifeCycle.java")
          .addTemplate("LogstashTcpProperties.java")
        .and()
        .batch(SOURCE.append("test"), toSrcTestJava().append(packagePath).append(LOGSTASH_SECONDARY))
          .addTemplate("LogstashTcpConfigurationIT.java")
          .addTemplate("LogstashTcpConfigurationTest.java")
          .addTemplate("LogstashTcpLifeCycleTest.java")
          .addTemplate("LogstashTcpPropertiesTest.java")
          .and()
        .and()
      .springMainProperties()
        .set(propertyKey("application.logging.logstash.tcp.enabled"), propertyValue(false))
        .set(propertyKey("application.logging.logstash.tcp.host"), propertyValue("localhost"))
        .set(propertyKey("application.logging.logstash.tcp.port"), propertyValue(5000))
        .set(propertyKey("application.logging.logstash.tcp.ring-buffer-size"), propertyValue(8192))
        .set(propertyKey("application.logging.logstash.tcp.shutdown_grace_period"), propertyValue("PT1M"))
        .and()
      .springTestLogger("net.logstash.logback", LogLevel.ERROR)
      .springTestLogger("org.jboss.logging", LogLevel.WARN)
      .build();
    // @formatter:on
  }
}
