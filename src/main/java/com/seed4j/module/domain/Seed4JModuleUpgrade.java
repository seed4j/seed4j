package com.seed4j.module.domain;

import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JDestinations;
import com.seed4j.module.domain.replacement.ElementReplacer;
import com.seed4j.module.domain.replacement.Seed4JUpgradeFilesReplacement;
import com.seed4j.module.domain.replacement.Seed4JUpgradeFilesReplacements;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class Seed4JModuleUpgrade {

  private final Seed4JDestinations skippedFiles;
  private final Seed4JProjectFilesPaths filesToDelete;
  private final Seed4JUpgradeFilesReplacements replacements;

  private Seed4JModuleUpgrade(Seed4JModuleUpgradeBuilder builder) {
    skippedFiles = new Seed4JDestinations(builder.skippedFiles);
    filesToDelete = new Seed4JProjectFilesPaths(builder.filesToDelete);
    replacements = new Seed4JUpgradeFilesReplacements(builder.replacements);
  }

  public static Seed4JModuleUpgradeBuilder builder() {
    return new Seed4JModuleUpgradeBuilder();
  }

  public Seed4JDestinations skippedFiles() {
    return skippedFiles;
  }

  public Seed4JProjectFilesPaths filesToDelete() {
    return filesToDelete;
  }

  public Seed4JUpgradeFilesReplacements replacements() {
    return replacements;
  }

  public static class Seed4JModuleUpgradeBuilder {

    private final Collection<Seed4JDestination> skippedFiles = new ArrayList<>();
    private final Collection<Seed4JProjectFilePath> filesToDelete = new ArrayList<>();
    private final Collection<Seed4JUpgradeFilesReplacement> replacements = new ArrayList<>();

    public Seed4JModuleUpgradeBuilder doNotAdd(Seed4JDestination file) {
      Assert.notNull("file", file);

      skippedFiles.add(file);

      return this;
    }

    public Seed4JModuleUpgradeBuilder delete(Seed4JProjectFilePath path) {
      Assert.notNull("path", path);

      filesToDelete.add(path);

      return this;
    }

    public Seed4JModuleUpgradeBuilder replace(Seed4JFileMatcher files, ElementReplacer replacer, String replacement) {
      replacements.add(new Seed4JUpgradeFilesReplacement(files, replacer, replacement));

      return this;
    }

    public Seed4JModuleUpgrade build() {
      return new Seed4JModuleUpgrade(this);
    }
  }
}
