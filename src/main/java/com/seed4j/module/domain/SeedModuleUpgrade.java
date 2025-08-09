package com.seed4j.module.domain;

import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedDestinations;
import com.seed4j.module.domain.replacement.ElementReplacer;
import com.seed4j.module.domain.replacement.SeedUpgradeFilesReplacement;
import com.seed4j.module.domain.replacement.SeedUpgradeFilesReplacements;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class SeedModuleUpgrade {

  private final SeedDestinations skippedFiles;
  private final SeedProjectFilesPaths filesToDelete;
  private final SeedUpgradeFilesReplacements replacements;

  private SeedModuleUpgrade(SeedModuleUpgradeBuilder builder) {
    skippedFiles = new SeedDestinations(builder.skippedFiles);
    filesToDelete = new SeedProjectFilesPaths(builder.filesToDelete);
    replacements = new SeedUpgradeFilesReplacements(builder.replacements);
  }

  public static SeedModuleUpgradeBuilder builder() {
    return new SeedModuleUpgradeBuilder();
  }

  public SeedDestinations skippedFiles() {
    return skippedFiles;
  }

  public SeedProjectFilesPaths filesToDelete() {
    return filesToDelete;
  }

  public SeedUpgradeFilesReplacements replacements() {
    return replacements;
  }

  public static class SeedModuleUpgradeBuilder {

    private final Collection<SeedDestination> skippedFiles = new ArrayList<>();
    private final Collection<SeedProjectFilePath> filesToDelete = new ArrayList<>();
    private final Collection<SeedUpgradeFilesReplacement> replacements = new ArrayList<>();

    public SeedModuleUpgradeBuilder doNotAdd(SeedDestination file) {
      Assert.notNull("file", file);

      skippedFiles.add(file);

      return this;
    }

    public SeedModuleUpgradeBuilder delete(SeedProjectFilePath path) {
      Assert.notNull("path", path);

      filesToDelete.add(path);

      return this;
    }

    public SeedModuleUpgradeBuilder replace(SeedFileMatcher files, ElementReplacer replacer, String replacement) {
      replacements.add(new SeedUpgradeFilesReplacement(files, replacer, replacement));

      return this;
    }

    public SeedModuleUpgrade build() {
      return new SeedModuleUpgrade(this);
    }
  }
}
