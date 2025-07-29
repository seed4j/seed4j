package com.seed4j.module.domain;

import static java.util.stream.Collectors.joining;

import com.seed4j.shared.error.domain.Assert;
import java.util.stream.Stream;

/**
 * Pre-commit commands in the format supported by lint-staged.
 * Can be a single command, or a list of commands, or a JavaScript function.
 */
public record PreCommitCommands(String commands) {
  public PreCommitCommands {
    Assert.notBlank("commands", commands);
  }

  public static PreCommitCommands of(String... commands) {
    Assert.notEmpty("commands", commands);

    if (commands.length == 1) {
      String command = commands[0];
      if (command.matches("^\\[.*]$")) {
        return new PreCommitCommands(command);
      }
      return new PreCommitCommands(withQuotes(command));
    }
    return new PreCommitCommands(Stream.of(commands).map(PreCommitCommands::withQuotes).collect(joining(", ", "[", "]")));
  }

  private static String withQuotes(String command) {
    if (command.matches("^'.*'$")) {
      return command;
    }
    return "'%s'".formatted(command);
  }

  public String get() {
    return commands;
  }

  @Override
  public String toString() {
    return commands;
  }
}
