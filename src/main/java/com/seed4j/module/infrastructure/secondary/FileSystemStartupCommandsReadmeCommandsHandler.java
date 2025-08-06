package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.path;

import com.seed4j.module.domain.JHipsterModuleContext;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.module.domain.replacement.*;
import com.seed4j.module.domain.startupcommand.JHipsterStartupCommand;
import com.seed4j.module.domain.startupcommand.JHipsterStartupCommands;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Service;

@Service
class FileSystemStartupCommandsReadmeCommandsHandler {

  private static final JHipsterProjectFilePath README = path("README.md");
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

  public void handle(JHipsterProjectFolder projectFolder, JHipsterStartupCommands commands, JHipsterModuleContext context) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("commands", commands);

    if (!commands.get().isEmpty()) {
      handleCommandsForProjectType(projectFolder, commands, context);
    }
  }

  private void handleCommandsForProjectType(
    JHipsterProjectFolder projectFolder,
    JHipsterStartupCommands commands,
    JHipsterModuleContext context
  ) {
    commands.get().forEach(command -> addCommandToReadme(projectFolder, command, context));
  }

  private void addCommandToReadme(JHipsterProjectFolder projectFolder, JHipsterStartupCommand command, JHipsterModuleContext context) {
    String replacedTemplate = BASH_TEMPLATE.replace("{{command}}", command.commandLine().get());
    OptionalReplacer replacer = new OptionalReplacer(JHIPSTER_STARTUP_COMMAND_SECTION_NEEDLE, replacedTemplate);
    fileReplacer.handle(projectFolder, ContentReplacers.of(new OptionalFileReplacer(README, replacer)), context);
  }
}
