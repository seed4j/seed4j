package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.path;

import com.seed4j.module.domain.Seed4JModuleContext;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.replacement.*;
import com.seed4j.module.domain.startupcommand.Seed4JStartupCommand;
import com.seed4j.module.domain.startupcommand.Seed4JStartupCommands;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Service;

@Service
class FileSystemStartupCommandsReadmeCommandsHandler {

  private static final Seed4JProjectFilePath README = path("README.md");
  private static final TextNeedleBeforeReplacer SEED4J_STARTUP_COMMAND_SECTION_NEEDLE = lineBeforeText(
    "\n<!-- seed4j-needle-startupCommand -->"
  );
  private static final String BASH_TEMPLATE = """
    ```bash
    {{command}}
    ```
    """;
  private final FileSystemReplacer fileReplacer;

  public FileSystemStartupCommandsReadmeCommandsHandler(FileSystemReplacer fileReplacer) {
    this.fileReplacer = fileReplacer;
  }

  public void handle(Seed4JProjectFolder projectFolder, Seed4JStartupCommands commands, Seed4JModuleContext context) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("commands", commands);

    if (!commands.get().isEmpty()) {
      handleCommandsForProjectType(projectFolder, commands, context);
    }
  }

  private void handleCommandsForProjectType(
    Seed4JProjectFolder projectFolder,
    Seed4JStartupCommands commands,
    Seed4JModuleContext context
  ) {
    commands.get().forEach(command -> addCommandToReadme(projectFolder, command, context));
  }

  private void addCommandToReadme(Seed4JProjectFolder projectFolder, Seed4JStartupCommand command, Seed4JModuleContext context) {
    String replacedTemplate = BASH_TEMPLATE.replace("{{command}}", command.commandLine().get());
    OptionalReplacer replacer = new OptionalReplacer(SEED4J_STARTUP_COMMAND_SECTION_NEEDLE, replacedTemplate);
    fileReplacer.handle(projectFolder, ContentReplacers.of(new OptionalFileReplacer(README, replacer)), context);
  }
}
