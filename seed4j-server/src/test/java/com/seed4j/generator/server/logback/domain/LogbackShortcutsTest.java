package com.seed4j.generator.server.logback.domain;

import static com.seed4j.generator.server.logback.domain.LogbackShortcuts.logbackLogger;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.testLogbackFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class LogbackShortcutsTest {

  @Test
  void shouldAddLoggerBeforeNeedle() {
    Seed4JModule module = moduleBuilder(properties())
      .with(logbackLogger(path("src/test/resources/logback.xml"), "org.hibernate", LogLevel.WARN))
      .build();

    assertThatModuleWithFiles(module, testLogbackFile())
      .hasFile("src/test/resources/logback.xml")
      .containing(
        """
          <logger name="org.hibernate" level="WARN" />
          <!-- seed4j-needle-logback-add-log -->
        """
      );
  }

  @Test
  void shouldIgnoreMissingLogbackFile() {
    Seed4JModule module = moduleBuilder(properties())
      .with(logbackLogger(path("src/test/resources/logback-test.xml"), "org.hibernate", LogLevel.WARN))
      .build();

    assertThatModule(module).doNotHaveFiles("src/test/resources/logback-test.xml");
  }

  private static Seed4JModuleProperties properties() {
    return Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
  }
}
