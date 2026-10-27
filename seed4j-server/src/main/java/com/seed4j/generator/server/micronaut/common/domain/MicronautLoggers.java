package com.seed4j.generator.server.micronaut.common.domain;

import static com.seed4j.generator.server.logback.domain.LogbackShortcuts.logbackLogger;
import static com.seed4j.module.domain.Seed4JModule.path;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import java.util.function.Consumer;

public final class MicronautLoggers {

  private static final Seed4JProjectFilePath MICRONAUT_MAIN_LOGBACK_FILE = path("src/main/resources/logback.xml");
  private static final Seed4JProjectFilePath MICRONAUT_TEST_LOGBACK_FILE = path("src/test/resources/logback-test.xml");

  private MicronautLoggers() {}

  public static Consumer<Seed4JModuleBuilder> micronautMainLogger(String name, LogLevel level) {
    return logbackLogger(MICRONAUT_MAIN_LOGBACK_FILE, name, level);
  }

  public static Consumer<Seed4JModuleBuilder> micronautTestLogger(String name, LogLevel level) {
    return logbackLogger(MICRONAUT_TEST_LOGBACK_FILE, name, level);
  }
}
