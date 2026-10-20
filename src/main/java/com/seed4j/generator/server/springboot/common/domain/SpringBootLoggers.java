package com.seed4j.generator.server.springboot.common.domain;

import static com.seed4j.generator.server.logback.domain.LogbackShortcuts.logbackLogger;
import static com.seed4j.module.domain.Seed4JModule.path;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import java.util.function.Consumer;

public final class SpringBootLoggers {

  private static final Seed4JProjectFilePath SPRING_MAIN_LOGBACK_FILE = path("src/main/resources/logback-spring.xml");
  private static final Seed4JProjectFilePath SPRING_TEST_LOGBACK_FILE = path("src/test/resources/logback.xml");

  private SpringBootLoggers() {}

  public static Consumer<Seed4JModuleBuilder> springMainLogger(String name, LogLevel level) {
    return logbackLogger(SPRING_MAIN_LOGBACK_FILE, name, level);
  }

  public static Consumer<Seed4JModuleBuilder> springTestLogger(String name, LogLevel level) {
    return logbackLogger(SPRING_TEST_LOGBACK_FILE, name, level);
  }
}
