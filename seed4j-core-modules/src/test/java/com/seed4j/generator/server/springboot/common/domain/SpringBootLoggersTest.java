package com.seed4j.generator.server.springboot.common.domain;

import static com.seed4j.generator.server.springboot.common.domain.SpringBootLoggers.springMainLogger;
import static com.seed4j.generator.server.springboot.common.domain.SpringBootLoggers.springTestLogger;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.logbackFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.testLogbackFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootLoggersTest {

  @Test
  void shouldAddMainLoggerToLogbackSpringFile() {
    Seed4JModule module = moduleBuilder(properties()).with(springMainLogger("org.hibernate", LogLevel.WARN)).build();

    assertThatModuleWithFiles(module, logbackFile())
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.hibernate\" level=\"WARN\" />");
  }

  @Test
  void shouldAddTestLoggerToTestLogbackFile() {
    Seed4JModule module = moduleBuilder(properties()).with(springTestLogger("org.hibernate", LogLevel.OFF)).build();

    assertThatModuleWithFiles(module, testLogbackFile())
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.hibernate\" level=\"OFF\" />");
  }

  @Test
  @SuppressWarnings("removal")
  void shouldAddMainLoggerWithDeprecatedBuilderMethod() {
    Seed4JModule module = moduleBuilder(properties()).springMainLogger("org.hibernate", LogLevel.WARN).build();

    assertThatModuleWithFiles(module, logbackFile())
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.hibernate\" level=\"WARN\" />");
  }

  @Test
  @SuppressWarnings("removal")
  void shouldAddTestLoggerWithDeprecatedBuilderMethod() {
    Seed4JModule module = moduleBuilder(properties()).springTestLogger("org.hibernate", LogLevel.OFF).build();

    assertThatModuleWithFiles(module, testLogbackFile())
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.hibernate\" level=\"OFF\" />");
  }

  private static Seed4JModuleProperties properties() {
    return Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
  }
}
