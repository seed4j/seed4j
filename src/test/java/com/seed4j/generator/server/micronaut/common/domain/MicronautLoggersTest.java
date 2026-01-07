package com.seed4j.generator.server.micronaut.common.domain;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautLoggers.micronautMainLogger;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautLoggers.micronautTestLogger;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.fileFromClasspath;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.ModuleFile;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautLoggersTest {

  private static final String LOGBACK_WITH_NEEDLE = "generator/server/springboot/core/test/logback.xml.mustache";

  @Test
  void shouldAddMainLoggerToLogbackFile() {
    Seed4JModule module = moduleBuilder(properties()).with(micronautMainLogger("org.hibernate", LogLevel.WARN)).build();

    assertThatModuleWithFiles(module, logbackFile("src/main/resources/logback.xml"))
      .hasFile("src/main/resources/logback.xml")
      .containing("<logger name=\"org.hibernate\" level=\"WARN\" />");
  }

  @Test
  void shouldAddTestLoggerToLogbackTestFile() {
    Seed4JModule module = moduleBuilder(properties()).with(micronautTestLogger("org.hibernate", LogLevel.OFF)).build();

    assertThatModuleWithFiles(module, logbackFile("src/test/resources/logback-test.xml"))
      .hasFile("src/test/resources/logback-test.xml")
      .containing("<logger name=\"org.hibernate\" level=\"OFF\" />");
  }

  private static ModuleFile logbackFile(String destination) {
    return fileFromClasspath(LOGBACK_WITH_NEEDLE, destination);
  }

  private static Seed4JModuleProperties properties() {
    return Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
  }
}
