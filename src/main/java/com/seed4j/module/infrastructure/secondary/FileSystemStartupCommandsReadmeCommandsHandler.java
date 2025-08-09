package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.SeedModule.lineBeforeText;
import static com.seed4j.module.domain.SeedModule.path;

import com.seed4j.module.domain.SeedModuleContext;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.module.domain.replacement.*;
import com.seed4j.module.domain.startupcommand.SeedStartupCommand;
import com.seed4j.module.domain.startupcommand.SeedStartupCommands;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Service;

@Service
class FileSystemStartupCommandsReadmeCommandsHandler {

  private static final SeedProjectFilePath README = path("README.md");
  private static final TextNeedleBeforeReplacer JHIPSTER_STARTUP_COMMAND_SECTION_NEEDLE = lineBeforeText(
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

  public void handle(SeedProjectFolder projectFolder, SeedStartupCommands commands, SeedModuleContext context) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("commands", commands);

    if (!commands.get().isEmpty()) {
      handleCommandsForProjectType(projectFolder, commands, context);
    }
  }

  private void handleCommandsForProjectType(SeedProjectFolder projectFolder, SeedStartupCommands commands, SeedModuleContext context) {
    commands.get().forEach(command -> addCommandToReadme(projectFolder, command, context));
  }

  private void addCommandToReadme(SeedProjectFolder projectFolder, SeedStartupCommand command, SeedModuleContext context) {
    String replacedTemplate = BASH_TEMPLATE.replace("{{command}}", command.commandLine().get());
    OptionalReplacer replacer = new OptionalReplacer(JHIPSTER_STARTUP_COMMAND_SECTION_NEEDLE, replacedTemplate);
    fileReplacer.handle(projectFolder, ContentReplacers.of(new OptionalFileReplacer(README, replacer)), context);
  }
}
