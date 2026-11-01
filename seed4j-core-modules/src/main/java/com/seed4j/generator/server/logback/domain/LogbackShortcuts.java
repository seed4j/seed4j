package com.seed4j.generator.server.logback.domain;

import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.replacement.OptionalReplacer;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;

public final class LogbackShortcuts {

  private static final TextNeedleBeforeReplacer SEED4J_LOGGER_NEEDLE = lineBeforeText("<!-- seed4j-needle-logback-add-log -->");

  private LogbackShortcuts() {}

  /**
   * Add a logger to a logback configuration file, before its {@code <!-- seed4j-needle-logback-add-log -->} needle. Nothing is
   * done if the file or the needle doesn't exist.
   */
  public static Consumer<Seed4JModuleBuilder> logbackLogger(Seed4JProjectFilePath file, String name, LogLevel level) {
    Assert.notNull("file", file);
    Assert.notBlank("name", name);
    Assert.notNull("level", level);

    return builder ->
      builder
        .optionalReplacements()
        .in(file)
        .add(logConfigurationEntry(name, level, builder.indentation()));
  }

  private static OptionalReplacer logConfigurationEntry(String name, LogLevel level, Indentation indentation) {
    return new OptionalReplacer(SEED4J_LOGGER_NEEDLE, logger(name, level, indentation));
  }

  private static String logger(String name, LogLevel level, Indentation indentation) {
    return "%s<logger name=\"%s\" level=\"%s\" />".formatted(indentation.spaces(), name, level.value());
  }
}
