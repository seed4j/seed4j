package tech.jhipster.lite.generator.server.springboot.logging.logstash.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;

public class LogstashAssert {

  public static void assertDependencies(Project project) {
    assertFileContent(project, POM_XML, List.of("<logstash-logback-encoder.version>"));
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>net.logstash.logback</groupId>",
        "<artifactId>logstash-logback-encoder</artifactId>",
        "<version>${logstash-logback-encoder.version}</version>",
        "</dependency>"
      )
    );
  }

  public static void assertJavaFiles(Project project) {
    String basePackage = project.getPackageName().orElse("com.mycompany.myapp");
    String logstashPackage = basePackage + ".technical.infrastructure.secondary.logstash";

    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String logstashPath = getPath(MAIN_JAVA, basePath, "technical/infrastructure/secondary/logstash");

    assertFileExist(project, getPath(logstashPath, "LogstashTcpConfiguration.java"));
    assertFileExist(project, getPath(logstashPath, "LogstashTcpLifeCycle.java"));
    assertFileExist(project, getPath(logstashPath, "LogstashTcpProperties.java"));

    assertFileContent(project, getPath(logstashPath, "LogstashTcpConfiguration.java"), "package " + logstashPackage);
    assertFileContent(project, getPath(logstashPath, "LogstashTcpLifeCycle.java"), "package " + logstashPackage);
    assertFileContent(project, getPath(logstashPath, "LogstashTcpProperties.java"), "package " + logstashPackage);

    String logstashTestPath = getPath(TEST_JAVA, basePath, "technical/infrastructure/secondary/logstash");

    assertFileExist(project, getPath(logstashTestPath, "LogstashTcpConfigurationIT.java"));
    assertFileExist(project, getPath(logstashTestPath, "LogstashTcpConfigurationTest.java"));
    assertFileExist(project, getPath(logstashTestPath, "LogstashTcpLifeCycleTest.java"));
    assertFileExist(project, getPath(logstashTestPath, "LogstashTcpPropertiesTest.java"));

    assertFileContent(project, getPath(logstashTestPath, "LogstashTcpConfigurationIT.java"), "package " + logstashPackage);
    assertFileContent(project, getPath(logstashTestPath, "LogstashTcpConfigurationTest.java"), "package " + logstashPackage);
    assertFileContent(project, getPath(logstashTestPath, "LogstashTcpLifeCycleTest.java"), "package " + logstashPackage);
    assertFileContent(project, getPath(logstashTestPath, "LogstashTcpPropertiesTest.java"), "package " + logstashPackage);
  }

  public static void assertProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/application.properties"),
      List.of(
        "application.logging.logstash.tcp.enabled=false",
        "application.logging.logstash.tcp.host=localhost",
        "application.logging.logstash.tcp.port=5000",
        "application.logging.logstash.tcp.ring-buffer-size=8192",
        "application.logging.logstash.tcp.shutdown_grace_period=PT1M"
      )
    );
  }

  public static void assertLoggerInConfiguration(Project project) {
    assertFileContent(project, getPath(TEST_RESOURCES, LOGGING_TEST_CONFIGURATION), "<logger name=\"org.jboss.logging\" level=\"WARN\" />");
  }
}
