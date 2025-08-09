package com.seed4j.module.domain;

import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedDestinations;
import com.seed4j.module.domain.replacement.ElementReplacer;
import com.seed4j.module.domain.replacement.JHipsterUpgradeFilesReplacement;
import com.seed4j.module.domain.replacement.JHipsterUpgradeFilesReplacements;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class JHipsterModuleUpgrade {

  private final SeedDestinations skippedFiles;
  private final JHipsterProjectFilesPaths filesToDelete;
  private final JHipsterUpgradeFilesReplacements replacements;

  private JHipsterModuleUpgrade(JHipsterModuleUpgradeBuilder builder) {
    skippedFiles = new SeedDestinations(builder.skippedFiles);
    filesToDelete = new JHipsterProjectFilesPaths(builder.filesToDelete);
    replacements = new JHipsterUpgradeFilesReplacements(builder.replacements);
  }

  public static JHipsterModuleUpgradeBuilder builder() {
    return new JHipsterModuleUpgradeBuilder();
  }

  public SeedDestinations skippedFiles() {
    return skippedFiles;
  }

  public JHipsterProjectFilesPaths filesToDelete() {
    return filesToDelete;
  }

  public JHipsterUpgradeFilesReplacements replacements() {
    return replacements;
  }

  public static class JHipsterModuleUpgradeBuilder {

    private final Collection<SeedDestination> skippedFiles = new ArrayList<>();
    private final Collection<SeedProjectFilePath> filesToDelete = new ArrayList<>();
    private final Collection<JHipsterUpgradeFilesReplacement> replacements = new ArrayList<>();

    public JHipsterModuleUpgradeBuilder doNotAdd(SeedDestination file) {
      Assert.notNull("file", file);

      skippedFiles.add(file);

      return this;
    }

    public JHipsterModuleUpgradeBuilder delete(SeedProjectFilePath path) {
      Assert.notNull("path", path);

      filesToDelete.add(path);

      return this;
    }

    public JHipsterModuleUpgradeBuilder replace(JHipsterFileMatcher files, ElementReplacer replacer, String replacement) {
      replacements.add(new JHipsterUpgradeFilesReplacement(files, replacer, replacement));

      return this;
    }

    public JHipsterModuleUpgrade build() {
      return new JHipsterModuleUpgrade(this);
    }
  }
}
