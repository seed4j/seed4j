package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.replacement.ReplacementCondition.notContainingReplacement;

import com.seed4j.module.domain.Seed4JModuleContext;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.gitignore.GitIgnoreEntry;
import com.seed4j.module.domain.gitignore.Seed4JModuleGitIgnore;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.replacement.*;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
class FileSystemGitIgnoreHandler {

  private static final String GIT_IGNORE_FILE_PATH = ".gitignore";
  private final FileSystemReplacer fileReplacer;

  public FileSystemGitIgnoreHandler(FileSystemReplacer fileReplacer) {
    this.fileReplacer = fileReplacer;
  }

  public void handle(Seed4JProjectFolder projectFolder, Seed4JModuleGitIgnore gitIgnore) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("gitIgnore", gitIgnore);

    if (gitIgnore.isNotEmpty()) {
      createGitIgnoreFileIfNeeded(projectFolder);
    }
    gitIgnore.forEach(handleIgnorePattern(projectFolder));
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "IOException is hard to test")
  private static void createGitIgnoreFileIfNeeded(Seed4JProjectFolder projectFolder) {
    Path gitIgnoreFilePath = projectFolder.filePath(GIT_IGNORE_FILE_PATH);

    if (Files.notExists(gitIgnoreFilePath)) {
      try {
        Files.createFile(gitIgnoreFilePath);
      } catch (IOException exception) {
        throw GeneratorException.technicalError(
          "Error creating %s file: %s".formatted(GIT_IGNORE_FILE_PATH, exception.getMessage()),
          exception
        );
      }
    }
  }

  private Consumer<GitIgnoreEntry> handleIgnorePattern(Seed4JProjectFolder projectFolder) {
    return gitIgnoreEntry -> {
      MandatoryReplacer replacer = new MandatoryReplacer(new EndOfFileReplacer(notContainingReplacement()), gitIgnoreEntry.get());
      fileReplacer.handle(
        projectFolder,
        ContentReplacers.of(new MandatoryFileReplacer(new Seed4JProjectFilePath(GIT_IGNORE_FILE_PATH), replacer)),
        Seed4JModuleContext.empty()
      );
    };
  }
}
